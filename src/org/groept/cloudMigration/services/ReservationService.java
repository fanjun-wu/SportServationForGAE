package org.groept.cloudMigration.services;

import java.util.List;

import org.groept.cloudMigration.domain.Reservation;
import org.springframework.stereotype.Component;




import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;

@Component
public class ReservationService {

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	public List<Reservation> getReservations() {
		
		
		return ofy.query(Reservation.class).list();
	}
	
	
	public Reservation getReservation(final Long reservationId) {
		
		return ofy.get(new Key<Reservation>(Reservation.class, reservationId));
	}
	
	
	
	public Reservation saveReservation(final Reservation reservation) {
	
		ofy.put(reservation);
		return reservation;
	}
	
	
	
	
	public void deleteReservation(final Long reservationId) {
		
		ofy.delete(Reservation.class, reservationId);
		
		
	}
	
	
	
	
	
}
