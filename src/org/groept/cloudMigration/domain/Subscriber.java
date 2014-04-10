package org.groept.cloudMigration.domain;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Subclass;




@Subclass
@Cached
public class Subscriber extends Person
{


	
	private String authenKey;
	
	
	

	public Subscriber(){
		
	}

	 
	

	
	
	public Subscriber(String name, String email, String telephone,
			String gender, int age,String authenKey) {
		super(name, email, telephone, gender, age);
		this.authenKey=authenKey;
		// TODO Auto-generated constructor stub
	}


	public String getAuthenKey() {
		return authenKey;
	}


	public void setAuthenKey(String authenKey) {
		this.authenKey = authenKey;
	}


	public void unsetAuthenKey() {
		this.authenKey = "";	
	}
	
	
	
	@Override
	public String toString() {
		return "subscriber: "+getName()+", "+getEmail();
	}

	
}

