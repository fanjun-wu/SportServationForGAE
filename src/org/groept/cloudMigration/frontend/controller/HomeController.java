package org.groept.cloudMigration.frontend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.groept.cloudMigration.domain.Admin;
import org.groept.cloudMigration.domain.Capability;
import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.domain.Hall;
import org.groept.cloudMigration.domain.Person;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.googlecode.objectify.ObjectifyService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private CapabilityService capabilityService;
	private TimeIntervalService timeIntervalService;
	private AdminService adminService;
	private CourtService courtService;
	private HallService hallService;
	private ReservationService reservationService;
	private SubscriberService subscriberService;
	
	
	@RequestMapping(value={"/reservation","/reservation/"},method=RequestMethod.GET)
	public String getReservationPage()
	{
		
		return "frontend/reservation";
	}
	
	
	@RequestMapping(value="/preset", method=RequestMethod.GET)
	public void home() {
		ObjectifyService.register(Court.class);
		ObjectifyService.register(Hall.class);
		ObjectifyService.register(Admin.class);
		ObjectifyService.register(Person.class);
		ObjectifyService.register(Capability.class);
		ObjectifyService.register(Reservation.class);
		ObjectifyService.register(Subscriber.class);
		ObjectifyService.register(TimeInterval.class);
		
		logger.info("Welcome home!");
		
	}
	
	@RequestMapping(value="/mail", method=RequestMethod.GET)
	public void Mail() {
		Sendgrid mail = new Sendgrid("cloudbees_fanjun-wu","kgdssm123");
		
		mail.setTo("fanjun.w@gmail.com")
	    .setFrom("fanjun.wu@student.kuleuven.be")
	    .setSubject("Subject goes here")
	    .setText("Hello11 World!")
	    .setHtml("<strong>Hello World!</strong>");
		try {
			mail.send();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Welcome home!");
		
	}
	
	
	/*@RequestMapping(value="/test", method=RequestMethod.GET)
	public ModelAndView test() {
		
		
		
		Collection<Court> courts = capabilityService.getCourts(capabilityService.getCapabilities().get(0).getId());
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("courts", courts);
		return new ModelAndView("court/courtList", model);
		
		
	}*/
	
	@RequestMapping(value={"/success","/success/"},method=RequestMethod.GET)
	public String getSuccessPage()
	{
		
		return "frontend/success";
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public void test() {
		Court c=new Court("B1","GOOG COURT");
		Court c2=new Court("B2","GOOG COURT");
		Court c3=new Court("B3","GOOG COURT");
		
		
		Hall h= new Hall("HA",9,13,"GOOD HALL");
		Hall h2= new Hall("HB",9,13,"GOOD HALL");
		Hall h3= new Hall("HC",9,13,"GOOD HALL");
		
		hallService.saveHall(h);
		hallService.saveHall(h2);
		hallService.saveHall(h3);
		
		c.setHallName(h.getName());
		c2.setHallName(h2.getName());
		c3.setHallName(h3.getName());
		
		Capability BK=new Capability("Basketball","good","no");
		Capability BD=new Capability("Badminton","good","no");
		
		
		
		
		c.addCapability(BD);
		c2.addCapability(BK);
		
		BK.addCourt(c2);
		BD.addCourt(c);
		
		
		courtService.saveCourt(c);
		courtService.saveCourt(c2);
		courtService.saveCourt(c3);
		
		Admin admin=new Admin("conna","conna@gmail.com","0483359884","M",23,"admin");
		
		admin.setHallName("HA");
		adminService.saveAdmin(admin);
		
		
		
		capabilityService.saveCapability(BD);
		capabilityService.saveCapability(BK);
		
		
		Reservation r1=new Reservation("book basket1","no");
		Reservation r2=new Reservation("book basket2","no");
		Reservation r3=new Reservation("book badminton1","no");
		Reservation r4=new Reservation("book badminton2","no");
		Reservation r5=new Reservation("book basket3","no");
		
		Subscriber s1=new Subscriber("lihua1","lihua1@gmail.com","0254846854","M",23,"dsqdq484sdqsdsq");
		Subscriber s2=new Subscriber("lihua2","lihua2@gmail.com","0254846854","M",23,"dsqdq484sdqsdsq");
		Subscriber s3=new Subscriber("lihua3","lihua3@gmail.com","0254846854","M",23,"dsqdq484sdqsdsq");
		Subscriber s4=new Subscriber("lihua4","lihua4@gmail.com","0254846854","M",23,"dsqdq484sdqsdsq");
		Subscriber s5=new Subscriber("lihua5","lihua5@gmail.com","0254846854","M",23,"dsqdq484sdqsdsq");
		
		r1.setSubcriberName(s1.getName());
		r2.setSubcriberName(s2.getName());
		r3.setSubcriberName(s3.getName());
		r4.setSubcriberName(s4.getName());
		r5.setSubcriberName(s5.getName());
		
		r1.setCourtName(c.getName());
		r2.setCourtName(c.getName());
		r3.setCourtName(c2.getName());
		r4.setCourtName(c2.getName());
		r5.setCourtName(c.getName());
		
		TimeInterval t1=new TimeInterval(9,getDateByString("2014-3-20"));
		TimeInterval t2=new TimeInterval(9,getDateByString("2014-3-20"));
		TimeInterval t3=new TimeInterval(10,getDateByString("2014-3-20"));
		TimeInterval t4=new TimeInterval(11,getDateByString("2014-3-20"));
		TimeInterval t5=new TimeInterval(12,getDateByString("2014-3-20"));
		
		r1.addTimeInterval(t1);
		r2.addTimeInterval(t2);
		r3.addTimeInterval(t3);
		r4.addTimeInterval(t4);
		r5.addTimeInterval(t5);
		
		t5.addReservation(r5);
		t4.addReservation(r4);
		t3.addReservation(r3);
		t2.addReservation(r2);
		t1.addReservation(r1);
		
		
		subscriberService.saveSubscriber(s1);  
		subscriberService.saveSubscriber(s2);  
		subscriberService.saveSubscriber(s3);  
		subscriberService.saveSubscriber(s4);  
		subscriberService.saveSubscriber(s5);  
		
		
		reservationService.saveReservation(r1); 		
		reservationService.saveReservation(r2); 
		reservationService.saveReservation(r3); 
		reservationService.saveReservation(r4); 
		reservationService.saveReservation(r5); 
		
		
		
		timeIntervalService.saveTimeInterval(t1);
		timeIntervalService.saveTimeInterval(t2);
		timeIntervalService.saveTimeInterval(t3);
		timeIntervalService.saveTimeInterval(t4);
		timeIntervalService.saveTimeInterval(t5);
	}
	

	

	@RequestMapping(value={"/home","/"," "},method=RequestMethod.GET)
	public String getHomePage()
	{
		
		return "frontend/home";
	}
/*
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() {

		ModelAndView model = new ModelAndView("home");
		return model;

	}
*/	

	@RequestMapping(value = {"/about","/about/"}, method = RequestMethod.GET)
	public String getAboutPage(Model model) {
		logger.info("About page !");
		
		return "frontend/about";
	}
	
	/*
	@RequestMapping(value = "/login/user-name/{userName}/user-authen-key/{userAuthenKey}", method = RequestMethod.GET)
	public String getUserPage(@PathVariable String userName,@PathVariable String userAuthenKey) {
		logger.info("About page !");
		Subscriber subscriber=new Subscriber();
		List<Subscriber> subs=subscriberService.getSubscribers();
		for(Subscriber sub:subs)
			
			if(sub.getAuthenKey().equals(userAuthenKey)&&sub.getName().equals(userName))
			{
				subscriber=sub;
				
				return "frontend/cancel-reservation";
			}
		
		return "frontend/not-found";
	}
	
	*/
	@RequestMapping(value = "/login/user-name/{userName}/user-authen-key/{userAuthenKey}", method = RequestMethod.GET)
	public ModelAndView getUserPage(@PathVariable String userName,@PathVariable String userAuthenKey) {
		logger.info("About page !");
		ModelAndView mav = new ModelAndView();
		for(Subscriber sub:(List<Subscriber>)subscriberService.getSubscribers())
			
			if(sub.getAuthenKey().equals(userAuthenKey)&&sub.getName().equals(userName))
			{
				System.out.println("we find you: "+userName);
				mav.addObject("subscriber",sub);
				mav.setViewName("frontend/cancel-reservation");
				return mav;
				
			}
		
		mav.setViewName("frontend/not-found");
		
		return mav;
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
		this.adminService = adminService;
	}
	@Autowired
	public void setCourtService(CourtService courtService) {
		this.courtService = courtService;
	}
	@Autowired
	public void setHallService(HallService hallService) {
		this.hallService = hallService;
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
