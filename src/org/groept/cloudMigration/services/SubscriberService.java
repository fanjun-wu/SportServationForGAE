package org.groept.cloudMigration.services;

import java.util.Collection;
import java.util.List;

import org.groept.cloudMigration.domain.Reservation;
import org.groept.cloudMigration.domain.Subscriber;
import org.springframework.stereotype.Component;







import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;


@Component
public class SubscriberService {

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	public List<Subscriber> getSubscribers() {
		
		
		return ofy.query(Subscriber.class).list();
	}
	
	
	public Subscriber getSubscriber(final Long subscriberId) {
		
		return ofy.get(new Key<Subscriber>(Subscriber.class, subscriberId));
	}
	
	
	
	public Subscriber saveSubscriber(final Subscriber subscriber) {
	
		ofy.put(subscriber);
		return subscriber;
	}
	
	
	
	
	public void deleteSubscriber(final Long subscriberId) {
		
		ofy.delete(Subscriber.class, subscriberId);
		
		
	}
	
	
	public Collection<Reservation> listofReservations(Long subscriberId) {
		// TODO Auto-generated method stub
		Subscriber s=getSubscriber(subscriberId);
		return ofy.query(Reservation.class).filter("subcriberName", s.getName()).list();
	}
	
	
}
