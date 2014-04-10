package org.groept.cloudMigration.domain;
import java.util.List;
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
public class Hall
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String name;
	
	private int openTime;
	
	private int closeTime;
	
	private String introduction;
	
	
	public List<Court> getCourt() {
		 ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		    Objectify ofy = ObjectifyService.begin(opts);
		return  ofy.query(Court.class).filter("hallName", this.name).list();
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpenTime() {
		return openTime;
	}

	public void setOpenTime(int openTime) {
		this.openTime = openTime;
	}

	public int getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(int closeTime) {
		this.closeTime = closeTime;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}




	public Hall(){
		
	}

	

	
	
	

	@Override
	public String toString() {
		return "Hall [id=" + id + ", name=" + name + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + ", introduction=" + introduction+ "]";
	}

	public Hall(String name, int openTime, int closeTime, String introduction) {		
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.introduction = introduction;
	}


	public Hall(Long id, String name, int openTime, int closeTime,
			String introduction) {
		
		this.id = id;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.introduction = introduction;
	}

	
	
	
	
}

