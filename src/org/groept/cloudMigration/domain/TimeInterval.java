package org.groept.cloudMigration.domain;
import java.util.Date;
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
public class TimeInterval
{
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int startTime;
	
	private String name;

	private Date date;
	 
	
	

	
	public List<Reservation> getReservation() {
		 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		    Objectify ofy = ObjectifyService.begin(opts);
		
		return  ofy.query(Reservation.class).filter("timeIntervals", this).list();
	}
	
	
	
	
	private Set<String> reservationsName;
	
 	
	
	
	public Set<String> getReservationsName() {
		if(reservationsName == null) {
			this.reservationsName = new HashSet<String>();
		}
		return reservationsName;
	}




	public void addReservation(Reservation reservation)
	{
		if(reservationsName == null) {
			this.reservationsName = new HashSet<String>();
		}
		reservationsName.add(reservation.getDiscription());
	}

	


	public TimeInterval(){
		
	}
    
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public TimeInterval(int startTime, Date date) {		
		this.startTime = startTime;
		this.date = date;
		this.name=""+date+"+"+startTime;
	}


	public TimeInterval(int start)
	{
		startTime=start;		
	}


	
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void unsetStartTime() {
		this.startTime = 0;	
	}
	
	 
	public void unsetDate() {
		this.date = new Date();	
	}
	 

	
	 
	public void unsetId() {
		this.id = null;	
	}


	
	@Override
	public String toString() {
	
	
		return "from "+startTime+"h to "+(startTime+1)+"h, "+date.toString().split(" ")[0];
	
	}
	

	
	
}

