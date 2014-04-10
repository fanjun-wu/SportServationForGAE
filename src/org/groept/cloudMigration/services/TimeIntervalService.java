package org.groept.cloudMigration.services;

import java.util.Date;
import java.util.List;

import org.groept.cloudMigration.domain.TimeInterval;
import org.springframework.stereotype.Component;






import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;


@Component
public class TimeIntervalService  {

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	public List<TimeInterval> getTimeIntervals() {
		
		
		return ofy.query(TimeInterval.class).list();
	}
	
	
	public TimeInterval getTimeInterval(final Long timeIntervalId) {
		
		return ofy.get(new Key<TimeInterval>(TimeInterval.class, timeIntervalId));
	}
	
	
	
	public TimeInterval saveTimeInterval(final TimeInterval timeInterval) {
	
		ofy.put(timeInterval);
		return timeInterval;
	}
	
	
	
	
	public void deleteTimeInterval(final Long timeIntervalId) {
		
		ofy.delete(TimeInterval.class, timeIntervalId);
		
		
	}


	public List<TimeInterval> getTimeIntervalByDate(Date dateSelected) {
		// TODO Auto-generated method stub
		
		
		return ofy.query(TimeInterval.class).filter("date", dateSelected).list();
	}
}
