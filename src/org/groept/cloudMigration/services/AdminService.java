package org.groept.cloudMigration.services;

import java.util.List;

import org.groept.cloudMigration.domain.Admin;
import org.springframework.stereotype.Component;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;

@Component
public class AdminService {

	   ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
	    Objectify ofy = ObjectifyService.begin(opts);
	
	public List<Admin> getAdmins() {
		
		
		return ofy.query(Admin.class).list();
	}
	
	
	public Admin getAdmin(final Long adminId) {
		
		return ofy.get(new Key<Admin>(Admin.class, adminId));
	}
	
	
	
	public Admin saveAdmin(final Admin admin) {
	
		ofy.put(admin);
		return admin;
	}
	
	
	
	
	public void deleteAdmin(final Long adminId) {
		
		ofy.delete(Admin.class, adminId);
		
		
	}
}
