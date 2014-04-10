package org.groept.cloudMigration.frontend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.groept.cloudMigration.domain.Capability;
import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.domain.Hall;
import org.groept.cloudMigration.domain.Reservation;
import org.groept.cloudMigration.domain.Subscriber;
import org.groept.cloudMigration.domain.TimeInterval;
import org.groept.cloudMigration.services.AdminService;
import org.groept.cloudMigration.services.CapabilityService;
import org.groept.cloudMigration.services.CourtService;
import org.groept.cloudMigration.services.HallService;
import org.groept.cloudMigration.services.ReservationService;
import org.groept.cloudMigration.services.SubscriberService;
import org.groept.cloudMigration.services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.labs.repackaged.org.json.JSONException;

@Controller
public class MainController {



	private CapabilityService capabilityService;
	private TimeIntervalService timeIntervalService;
	private CourtService courtService;
	private ReservationService reservationService;
	private SubscriberService subscriberService;
	
	private Capability _capability=new Capability();
	private List<TimeInterval> _timeIntervals = new ArrayList<TimeInterval>();
	
	private Set<Hall> _hallsAvailable = new HashSet<Hall>();
	private List<Court> _allCourtsThisCap = new ArrayList<Court>();
	private Set<Court> _courtsAvailable= new HashSet<Court>();
	private Set<Reservation> _reservations=new HashSet<Reservation>();
	
	private Set<Integer> _startTimeSetAvailable=new HashSet<Integer>();
	private Date dateSelected=null; 
	private String _startTimeRecieved=null;	
	private Subscriber _subscriber=new Subscriber();
	
	
	private String _nameOfHallRecieved=null;
	private String _nameOfCourtRecieved=null;
	
	Set<Integer> timeSetLeft=new HashSet<Integer>();
	//===================================================================================
	//===================================================================================
	//in /getCapabilities  GET ----> none variables calculated
	//in /getCapabilities  POST ----> _capability,_allCourtsThisCap
	//in /getDate POST  ----> dateSelected, _timeIntervals, _reservations
	//in /getTimeIntervals GET--->  _startTimeSetAvailable
	//in /getTimeIntervals POST--->  _startTimeRecieved
	//in /getHalls GET ---> _hallsAvailable
	//in /getHalls POST ---> _nameOfHallRecieved
	//in /getCourts GET ---> _courtsAvailable
	//in /getCourts POST ---> _nameOfCourtRecieved
	//in /getUserInfo  POST---> _subscriber
	//===================================================================================
	//===================================================================================
	

	MainController() {
		
	}


	

	
	
	@RequestMapping(value = "/getCapabilities", method = RequestMethod.GET)
	public @ResponseBody
	List<Capability> getCapabilities() {

		List<Capability> capabilitiesTemp = capabilityService.getCapabilities();
		
		List<Capability> capabilities=new ArrayList<Capability>();
		
		for(Capability cap:capabilitiesTemp)
		{
			//didn't show the court info, because if yes, there is an json .... error in this case????  
			capabilities.add(new Capability(Long.parseLong("4912910010679222"),cap.getResource(),cap.getCondition(),cap.getDiscription()));
			
		}		
		System.out.println("capability types: "+capabilities.toString());
		
		return capabilities;
				
	}



	
	@RequestMapping(value = "/getCapabilities", method = RequestMethod.POST)
	public @ResponseBody
	void getCapabilitySelected(@RequestBody String capability) {

		System.out.println("inside //getCapabilities  POST");
		System.out.println("Capability recieved: "+capability);
		
		for(int i=0;i<capabilityService.getCapabilities().size();i++)
		{
			Capability cap=(Capability)capabilityService.getCapabilities().get(i);
			if(cap.getResource().equalsIgnoreCase(capability))
				_capability=cap;			
		}
		
		//get all the courts can be used for this sport
		_allCourtsThisCap=capabilityService.getCourts(_capability.getResource());	
		System.out.println("size of courts for this sport: "+_allCourtsThisCap.size());
		
		cleanVariablesAfterSelectSports();
	}
	
	
	@RequestMapping(value="/getDate", method=RequestMethod.POST)
	public @ResponseBody void getDateSelected(@RequestBody String dateRecieved)
	{		

		System.out.println("inside /getDate  POST");
		System.out.println("Date recieved: "+dateRecieved);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			dateSelected = formatter.parse(dateRecieved);
		
			System.out.println("Date recieved after parse: "+dateSelected);
			_timeIntervals=timeIntervalService.getTimeIntervalByDate(dateSelected);
				
			
			//get all the reservations for this date
			if(_timeIntervals.size()!=0)
				for(TimeInterval tt:_timeIntervals)
				{
					if(tt.getReservation().size()!=0)
						for(Reservation res:tt.getReservation())
						{
							_reservations.add(res);					
						}

				}			
			
			System.out.println("TimeInterval(with same date) Size: "+_timeIntervals.size());
			
			System.out.println("Reservations size: "+_reservations.size()+" at "+dateRecieved);
			
			cleanVariablesAfterSelectDate();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//list whose element[start time] begins from open to close, and return the elements[start time] which are not inside theset of timeSetReserved
	public Set<Integer> timeSetLeft(int open,int close,Set<Integer> timeSetReserved)
	{		
		Set<Integer> timeSetStandard=new HashSet<Integer>();
		

		for(int i=open;i<close;i++)
			timeSetStandard.add(i);
		
		System.out.println("timeSetReserved size: "+timeSetReserved.size());
		
		for(Integer t:timeSetReserved)
			System.out.println(": "+t);
		
		if(timeSetReserved.size()!=0)
		{
			
			for(Integer itr:timeSetStandard)
			{
				if(!timeSetReserved.contains(itr))
					timeSetLeft.add(itr);
			}
			
		}
		else
			timeSetLeft=timeSetStandard;
		
		return timeSetLeft;
	}


	@RequestMapping(value = "/getTimeIntervals", method = RequestMethod.GET)
	public @ResponseBody
	Set<Integer> getTimeIntervals() {
		
		Set<Integer> _startTimeSetReservedSingleCourt=new HashSet<Integer>();
		//get the start time list available for this sport at this day
		List< Set<Integer> > _startTimeSetLeftTotal=new ArrayList<Set<Integer>>();
		
		for(Court ct:_allCourtsThisCap)
		{
			
			Set<Integer> _startTimeSetLeftSingleCourt=new HashSet<Integer>();				
			
			//get all the reservation for that court in that day and manipulate the start time Set			
			
			System.out.println("ct getReservation() size(): "+ct.getReservation().size());			
			if(ct.getReservation().size()!=0)
				for(Reservation res: ct.getReservation())
				{

//					System.out.println("getTimeInterval() size: "+res.getTimeInterval().toString());
					for(TimeInterval tvl:res.getTimeInterval())
					{
						if( compareTwoDate(tvl.getDate(),dateSelected))
						{
							_startTimeSetReservedSingleCourt.add(tvl.getStartTime());
						}
							
					}									
				}

			System.out.println("_startTimeSetReservedSingleCourt size: "+_startTimeSetReservedSingleCourt.size());
			
			
			_startTimeSetLeftSingleCourt=timeSetLeft(courtService.getHall(ct).getOpenTime(),courtService.getHall(ct).getCloseTime(),_startTimeSetReservedSingleCourt);
			//_startTimeSetLeftSingleCourt=timeSetLeft(9,17,_startTimeSetReservedSingleCourt);
			
			System.out.println("_startTimeSetLeftSingleCourt size: "+_startTimeSetLeftSingleCourt.size());
			
			_startTimeSetLeftTotal.add(_startTimeSetLeftSingleCourt);							
		}
		
		System.out.println("_startTimeSetLeftTotal size: "+_startTimeSetLeftTotal.size());
		
		for(Set<Integer> setElement:_startTimeSetLeftTotal )
			for(Integer element:setElement)
			{
				_startTimeSetAvailable.add(element);
				System.out.println("-->  "+element);
			}

		
	/*	_startTimeSetAvailable.add(8);
		_startTimeSetAvailable.add(9);
		_startTimeSetAvailable.add(14);
		_startTimeSetAvailable.add(15);*/
		System.out.println("_startTimeSetAvailable size: "+_startTimeSetAvailable.size());
		
		
		
		return _startTimeSetAvailable ;
	}

	
	
	@SuppressWarnings("deprecation")
	public boolean compareTwoDate(Date d1,Date d2)
	{
		if(d1.getYear()==d2.getYear()&&d1.getMonth()==d2.getMonth()&&d1.getDay()==d2.getDay())
			return true;
		else
			return false;
	}
	
	
	
	@RequestMapping(value = "/getTimeIntervals", method = RequestMethod.POST)
	public @ResponseBody
	void getTimeIntervalSelected(@RequestBody String interval) {
		
		_startTimeRecieved=interval;
		System.out.println("start time recieved: "+interval);
		
		cleanVariablesAfterSelectTimeInterval();
		
	}

	
	@RequestMapping(value = "/getHalls", method = RequestMethod.GET)
	public @ResponseBody
	Set<Hall> getHallAvailables() {
		//calculate the halls available
				for(Court ct:_allCourtsThisCap)
				{
					Set<Integer> startTimeReservedSingleCourt=new HashSet<Integer>();
					for(Reservation res:ct.getReservation())
					{
						for(TimeInterval tv:res.getTimeInterval())
						{							
							if(compareTwoDate(tv.getDate(),dateSelected))							
								startTimeReservedSingleCourt.add(tv.getStartTime());
							
						}
						
					}
					//if the start time user choose isn't in the set of start time set reserved, then add this court or hall as available
					if(!startTimeReservedSingleCourt.contains(Integer.parseInt(_startTimeRecieved)))
					{			
						_hallsAvailable.add(courtService.getHall(ct));	
						//_hallsAvailable.add(new Hall("Ha",9,17,"goog"));	
					}	
					
				}
				
				System.out.println("available halls size: "+_hallsAvailable.size());
				

				
		Set<Hall> hallTemp=new HashSet<Hall>();		
		for(Hall ha:_hallsAvailable)		
		{
			hallTemp.add(new Hall(Long.parseLong("4912910010679242"),ha.getName(),ha.getOpenTime(),ha.getCloseTime(),ha.getIntroduction()));			
		}		

		//don't return _hallsAvailable directly, throw away the relations here, 
		//just the hall info is needed, if not, we will get the json serialize error
		//hallTemp.add(new Hall("B1",9,17,"good"));
		return hallTemp;

	}
	
	
	@RequestMapping(value = "/getHalls", method = RequestMethod.POST)
	public @ResponseBody
	void getHallSelected(@RequestBody String hall) {

		_nameOfHallRecieved=hall;
		System.out.println("hall choosen: "+hall);
		
		cleanVariablesAfterSelectHall();
	}
	


	@RequestMapping(value = "/getCourts", method = RequestMethod.GET)
	public @ResponseBody
	Set<Court> getCourts() {

		
		Hall hallTemp=new Hall();
		for(Hall ha:_hallsAvailable)
		{
			if(ha.getName().equalsIgnoreCase(_nameOfHallRecieved))
			{
				hallTemp=ha;				
			}			
		}
		
		for(Court ct:hallTemp.getCourt())
		{
			Set<Integer> startTimeReservedSingleCourt=new HashSet<Integer>();
			
			for(Capability cap:ct.getCapability())
				if(cap.getResource().equalsIgnoreCase(_capability.getResource()))
				{
					System.out.println("capability: "+_capability.getResource());
					for(Reservation res:ct.getReservation())
					{
						for(TimeInterval tv:res.getTimeInterval())
						{
							if( compareTwoDate(tv.getDate(),dateSelected))
								startTimeReservedSingleCourt.add(tv.getStartTime());
							
						}
						
					}
					
					//if the start time user choose isn't in the set of start time set reserved, then add this court or hall as available
					if(!startTimeReservedSingleCourt.contains(Integer.parseInt(_startTimeRecieved)))
					{			
						System.out.println("this court can be reserved");
						_courtsAvailable.add(ct);				
					}
					
					
				}
			
				
			
		}
		
		
		Set<Court> courtTemp=new HashSet<Court>();
		for(Court ct:_courtsAvailable)
			courtTemp.add(new Court(ct.getName(),ct.getIntroduction()));
			
		return courtTemp;
	}
	
	@RequestMapping(value = "/getCourts", method = RequestMethod.POST)
	public @ResponseBody
	void getCourtSelected(@RequestBody String court) {

		_nameOfCourtRecieved=court;
		
		System.out.println("court selected: "+court);
		
	}
	
	
	@RequestMapping(value="/getUserInfo", method=RequestMethod.POST)
	public @ResponseBody void getUserInfo(@RequestBody String user)
	{				

		String[] userInfoTemp=user.split(","); 
		_subscriber.setName(userInfoTemp[0]);
		_subscriber.setEmail(userInfoTemp[1]);
		_subscriber.setTelephone(userInfoTemp[2]);
		UUID uniqueKey1= UUID.randomUUID();
		UUID uniqueKey2= UUID.randomUUID();
		String key=uniqueKey1.toString()+uniqueKey2.toString();
		_subscriber.setAuthenKey(key);
		System.out.println("user info:"+_subscriber.getName()+" "+_subscriber.getEmail()+" "+_subscriber.getTelephone());
		
		saveReservation();
		cleanVaribalesAfterSubmit();
		
	}
	
	
	public void saveReservation()
	{	
		int i=0;
		Collection<Subscriber> subscribers = subscriberService.getSubscribers();
		for(Subscriber s: subscribers)
		{
			if(s.getEmail().equals(_subscriber.getEmail()))
			{
				i++;
			}
		}
		
		if (i==0)
			{
				subscriberService.saveSubscriber(_subscriber);
			}
		TimeInterval tal=new TimeInterval(Integer.parseInt(_startTimeRecieved),dateSelected);
		Set<TimeInterval> tals=new HashSet<TimeInterval>();
		tals.add(tal);
		timeIntervalService.saveTimeInterval(tal);
		int startTimeRecieved = Integer.parseInt(_startTimeRecieved);
		Reservation res=new Reservation();
		res.setDiscription("You have reserved " +_capability.getResource()+" at "+ _nameOfCourtRecieved+" in " +_nameOfHallRecieved +" from "+ startTimeRecieved+" to "+(startTimeRecieved+1)+". See you there!");
		res.setTips("no");
		res.setCourtName(_nameOfCourtRecieved);
		res.setSubcriberName(_subscriber.getName());
		res.addTimeInterval(tal);
		tal.addReservation(res);
		reservationService.saveReservation(res);
		timeIntervalService.saveTimeInterval(tal);
		
		Sendgrid mail = new Sendgrid("cloudbees_fanjun-wu","kgdssm123");
		
		mail.setTo(_subscriber.getEmail())
	    .setFrom("fanjun.wu@greatSport.com")
	    .setSubject("Success Regiseter")
	    .setText("Hello11 World!")
	    .setHtml("<strong>Hello World!</strong>");
		try {
			mail.send();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

	

	
	public void cleanVaribalesAfterSubmit()
	{
		System.out.println("clean all after you reserve successfully!");
		
		_capability=new Capability();		
		_allCourtsThisCap.clear();
		
		dateSelected=null;
		_timeIntervals.clear();
		_reservations.clear();
		
		_startTimeSetAvailable.clear();
		_startTimeRecieved=null;
		
		_hallsAvailable.clear();
		_nameOfHallRecieved=null;
		
		_courtsAvailable.clear();
		_nameOfCourtRecieved=null;		
		
		_subscriber=new Subscriber();
		
		
	}
	
	public void cleanVariablesAfterSelectSports()
	{
		System.out.println("cleanVariablesAfterSelectSports");
		
		dateSelected=null;
		_timeIntervals.clear();
		_reservations.clear();
		
		_startTimeSetAvailable.clear();
		_startTimeRecieved=null;
		
		_hallsAvailable.clear();
		_nameOfHallRecieved=null;
		
		_courtsAvailable.clear();
		_nameOfCourtRecieved=null;		
		
		_subscriber=new Subscriber();

		
	}
	
	public void cleanVariablesAfterSelectDate()
	{
		System.out.println("cleanVariablesAfterSelectDate");
		_startTimeSetAvailable.clear();
		_startTimeRecieved=null;
		
		_hallsAvailable.clear();
		_nameOfHallRecieved=null;
		
		_courtsAvailable.clear();
		_nameOfCourtRecieved=null;		
		
		_subscriber=new Subscriber();
		
	}
	
	public void cleanVariablesAfterSelectTimeInterval()
	{
		System.out.println("cleanVariablesAfterSelectTimeInterval");
		_hallsAvailable.clear();
		_nameOfHallRecieved=null;
		
		_courtsAvailable.clear();
		_nameOfCourtRecieved=null;		
		
		_subscriber=new Subscriber();
		
	}
	public void cleanVariablesAfterSelectHall()
	{
		System.out.println("cleanVariablesAfterSelectHall");
		_courtsAvailable.clear();
		_nameOfCourtRecieved=null;		
		
		_subscriber=new Subscriber();
		
	}
	

	
	
	public Date getDateByString(String dateString)
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		
		Date date=new Date();
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;		
	}
	

	
	@Autowired
	public void setCapabilityService(CapabilityService capabilityService) {
		this.capabilityService = capabilityService;
	}
	@Autowired
	public void setTimeIntervalService(TimeIntervalService timeIntervalService) {
		this.timeIntervalService = timeIntervalService;
	}
	@Autowired
	public void setAdminService(AdminService adminService) {
	}
	@Autowired
	public void setCourtService(CourtService courtService) {
		this.courtService = courtService;
	}
	@Autowired
	public void setHallService(HallService hallService) {
	}
	@Autowired
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	@Autowired
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
}
