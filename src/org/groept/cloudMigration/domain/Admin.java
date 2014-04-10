package org.groept.cloudMigration.domain;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Subclass;



@Subclass
@Cached
public class Admin extends Person
{
	
	
	private String privilege;
	
	
	
					
	
	private String hallName;

	
	public String getPrivilege() {
		return privilege;
	}


	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}


	


	public String getHallName() {
		return hallName;
	}


	public void setHallName(String hallName) {
		this.hallName = hallName;
	}


	public Admin( String name, String email, String telephone,
			String gender, int age,String privilege) {
		super( name, email, telephone, gender, age);
		this.privilege=privilege;
		// TODO Auto-generated constructor stub
	}
	
	public Admin(){
		
	}


	
	
	

	public void unsetPrivilege() {
		this.privilege = "";	
	}
	
	
	
	
}

