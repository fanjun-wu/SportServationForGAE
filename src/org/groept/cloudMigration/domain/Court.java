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
public class Court
{
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String introduction;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void unsetIntroduction() {
		this.introduction = "";	
	}
	
	public void unsetId() {
		this.id = null;	
	}
	
	
	public List<Reservation> getReservation() {
		
		 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		    Objectify ofy = ObjectifyService.begin(opts);
		
		return ofy.query(Reservation.class).filter("courtName", this.name).list();
	}

	 
	public List<Capability> getCapability() {
		
		 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		    Objectify ofy = ObjectifyService.begin(opts);
		
		
		
		List<Capability> c= new ArrayList<Capability>();
		List<Capability> c2=ofy.query(Capability.class).list();
		for(Capability cc: c2)
		{
			
			for(String namee :cc.getCourtNames())
			{
				if(namee.equals(name))
				{
					c.add(cc);
				}
			}
			
			
		}
		return c;
		
	}
	
	private String hallName;
	
	
	
	
	private Set<String> capabilitiesName;

	
	
	
	public Set<String> getCapabilitiesName() {
		if(capabilitiesName == null) {
			this.capabilitiesName = new HashSet<String>();
		}
		return capabilitiesName;
	}

	public void setCapabilitiesName(Set<String> capabilitiesName) {
		this.capabilitiesName = capabilitiesName;
	}

	public void addCapability(Capability capability)
	{
		if(capabilitiesName == null) {
			this.capabilitiesName = new HashSet<String>();
		}
		capabilitiesName.add( capability.getResource());
	}
	
	
	
	public Court(){
		
	}

	
	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	
	
	
	
	
	
	
	

	
	

	
	
	
	

	

	public Court(String name, String introduction) {		
		this.name = name;
		this.introduction = introduction;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
	
	
}

