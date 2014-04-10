package org.groept.cloudMigration.domain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;


 
@Entity
@Cached
public class Reservation
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String discription;
	
	private String tips;
	
	public Reservation(){
		
	}
	
	public Reservation(String discription, String tips) {		
		this.discription = discription;
		this.tips = tips;
	}

	private String courtName;
	
	private String subcriberName;
	

	 
	
	private Set<String> timeIntervalsName;
	
	 
	
	
	public Set<String> getTimeIntervalsName() {
		if(timeIntervalsName== null) {
			this.timeIntervalsName = new HashSet<String>();
		}
		return timeIntervalsName;
	}

	public void addTimeInterval(TimeInterval timeInterval)
	{
		if(timeIntervalsName== null) {
			this.timeIntervalsName = new HashSet<String>();
		}
		timeIntervalsName.add(timeInterval.getName());
	}
	
	  
	public Set<String> getTimeIntervals() {
		if(timeIntervalsName == null) {
			this.timeIntervalsName = new HashSet<String>();
		}
		return timeIntervalsName;
	}

	

	public Long getId() {
		return id;
	}






	public String getSubcriberName() {
		return subcriberName;
	}

	public void setSubcriberName(String subcriberName) {
		this.subcriberName = subcriberName;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getDiscription() {
		return discription;
	}






	public void setDiscription(String discription) {
		this.discription = discription;
	}






	public String getTips() {
		return tips;
	}






	public void setTips(String tips) {
		this.tips = tips;
	}






	public void setId(Long id) {
		this.id = id;
	}


	public void unsetId() {
		this.id = null;	
	}

	

	 
	public void unsetDiscription() {
		this.discription = "";	
	}
	
	 
	public void unsetTips() {
		this.tips = "";	
	}

	
	public List<TimeInterval> getTimeInterval() 
	{
	
		 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		    Objectify ofy = ObjectifyService.begin(opts);
		
		
		
		List<TimeInterval> t= new ArrayList<TimeInterval>();
		List<TimeInterval> t2=ofy.query(TimeInterval.class).list();
		for(TimeInterval tt: t2)
		{
			
			for(String namee :tt.getReservationsName())
			{
				if(namee.equals(this.discription))
				{
					t.add(tt);
				}
			}
			
			
		}
		return t;
		
	}
	
	 

	

	
	
	

	
	
	
	
}

