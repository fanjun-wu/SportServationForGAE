package org.groept.cloudMigration.domain;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;


@Entity
@Cached
public class Capability
{
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	private String resource;

	public Capability(String resource, String conditionCap,
			String discriptionCap) {
		
		this.resource = resource;
		this.conditionCap = conditionCap;
		this.discriptionCap = discriptionCap;
	}


	
	public Capability(Long id, String resource, String conditionCap,
			String discriptionCap) {
		
		this.id = id;
		this.resource = resource;
		this.conditionCap = conditionCap;
		this.discriptionCap = discriptionCap;
	}



	private String conditionCap;
	
	private String discriptionCap;
	 
	 


	
	
	


	
	
	

	private Set<String> courtNames;
	
	public void addCourt(Court court)
	{
		if(courtNames == null) {
			this.courtNames = new HashSet<String>();
		}
		courtNames.add( court.getName());
	}
	
	


	public Set<String> getCourtNames() {
		if(courtNames == null) {
			this.courtNames = new HashSet<String>();
		}
		return courtNames;
	}



	public void setCourtNames(Set<String> courtNames) {
		this.courtNames = courtNames;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public String getResource() {
		return resource;
	}





	public void setResource(String resource) {
		this.resource = resource;
	}





	public String getCondition() {
		return conditionCap;
	}





	public void setCondition(String condition) {
		this.conditionCap = condition;
	}





	public String getDiscription() {
		return discriptionCap;
	}





	public void setDiscription(String discription) {
		this.discriptionCap = discription;
	}



	public Capability(){
		
	}


	
	public void unsetId() {
		this.id = null;	
	}

	

	

	public void unsetResource() {
		this.resource = "";	
	}
	

	public void unsetCondition() {
		this.conditionCap = "";	
	}
	

	public void unsetDiscription() {
		this.discriptionCap = "";	
	}

	


	










@Override
public String toString() {
	return "Capability [id=" + id + ", resource=" + resource
			+ ", conditionCap=" + conditionCap + ", discriptionCap="
			+ discriptionCap + "]";
}


	
	
}

