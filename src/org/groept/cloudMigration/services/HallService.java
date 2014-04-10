package org.groept.cloudMigration.services;

import java.util.Collection;
import java.util.List;

import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.domain.Hall;
import org.springframework.stereotype.Component;











import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;

@Component
public class HallService {

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	public List<Hall> getHalls() {
		
		
		return ofy.query(Hall.class).list();
	}
	
	
	public Hall getHall(final Long hallId) {
		
		return ofy.get(new Key<Hall>(Hall.class, hallId));
	}
	
	
	
	public Hall saveHall(final Hall hall) {
	
		ofy.put(hall);
		return hall;
	}
	
	
	
	
	public void deleteHall(final Long hallId) {
		
		ofy.delete(Hall.class, hallId);
		
		
	}
	
	
	public Collection<Court> getCourts(String hallName)
	{
		
		
		return ofy.query(Court.class).filter("hallName", hallName).list();
		
	}


	public Collection<Court> listofCourts(Long hallId) {
		// TODO Auto-generated method stub
		Hall h=getHall(hallId);
		return h.getCourt();
	}
	
	
	
	
}
