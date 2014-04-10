package org.groept.cloudMigration.services;

import java.util.Collection;
import java.util.List;

import org.groept.cloudMigration.domain.Capability;
import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.domain.Hall;
import org.groept.cloudMigration.domain.Reservation;
import org.springframework.stereotype.Component;












import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;


@Component
public class CourtService{

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	
	public List<Court> getCourts() {
		
		
		return ofy.query(Court.class).list();
	}
	
	
	public Court getCourt(final Long courtId) {
		
		return ofy.get(new Key<Court>(Court.class, courtId));
	}
	
	
	
	public Court saveCourt(final Court court) {
	
		ofy.put(court);
		return court;
	}
	
	
	
	
	public void deleteCourt(final Long courtId) {
		
		ofy.delete(Court.class, courtId);
		
		
	}
	
	
	public Collection<Reservation> getReservations(String courtName)
	{

		return ofy.query(Reservation.class).filter("courtName", courtName).list();	
	}


	public Court getCourtByName(String _nameOfCourtRecieved) {
		// TODO Auto-generated method stub
		return ofy.get(new Key<Court>(Court.class,  _nameOfCourtRecieved));
	}
	
	public Hall getHall(Court c)
	{
		
		
		return ofy.query(Hall.class).filter("name", c.getHallName()).list().get(0);
		
	}
	
	public Collection<Capability> listofCapabilitys(Long courtId) {
		// TODO Auto-generated method stub
		Court c=getCourt(courtId);
		return c.getCapability();
	}
	
}
