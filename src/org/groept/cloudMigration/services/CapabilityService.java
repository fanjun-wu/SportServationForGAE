package org.groept.cloudMigration.services;

import java.util.ArrayList;
import java.util.List;

import org.groept.cloudMigration.domain.Capability;
import org.groept.cloudMigration.domain.Court;
import org.springframework.stereotype.Component;








import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;


@Component
public class CapabilityService {

	 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	
	public List<Capability> getCapabilities() {
		
		
		return ofy.query(Capability.class).list();
	}
	
	
	public Capability getCapability(final Long capabilityId) {
		
		return ofy.get(new Key<Capability>(Capability.class, capabilityId));
	}
	
	
	
	public Capability saveCapability(final Capability capability) {
	
		ofy.put(capability);
		return capability;
	}
	
	
	
	
	public void deleteCapability(final Long capabilityId) {
		
		ofy.delete(Capability.class, capabilityId);
		
		
	}
	
	public List<Court> getCourts(final String resource)
	{
		
		List<Court> c= new ArrayList<Court>();
		List<Court> c2=ofy.query(Court.class).list();
		for(Court cc: c2)
		{
			
			for(String name :cc.getCapabilitiesName())
			{
				if(name.equals(resource))
				{
					c.add(cc);
				}
			}
			
			
		}
		return c;

		
	}
	
	
	
	
	
	
	
}
