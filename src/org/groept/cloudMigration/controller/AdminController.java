package org.groept.cloudMigration.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.groept.cloudMigration.domain.Admin;
import org.groept.cloudMigration.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private AdminService adminService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/adminList", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("Listing admins.");
		Collection<Admin> admins = adminService.getAdmins();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("admins", admins);
		return new ModelAndView("admin/adminList", model);
	}
	
	
	
	
	/**
	 * Fetch a admin by it's ID.
	 */
	@RequestMapping(value="/getAdmin", method=RequestMethod.GET)
	public ModelAndView fetchAdmin(@RequestParam("adminId") Long adminId) {
		logger.info("Fetching admin " + adminId);
		Admin admin = adminService.getAdmin(adminId);
		Map<String,Object>modelAndView = new HashMap<String,Object>();
		modelAndView.put("admin", admin);
		return new ModelAndView("admin/modifyAdmin", modelAndView);
	}
	
	
	/**
	 * Fetch a admin by it's ID.
	 */
	@RequestMapping(value="/deleteAdmin", method=RequestMethod.GET)
	public String deleteAdmin(@RequestParam("adminId") Long adminId) {
		logger.info("Deleting admin " + adminId);
		adminService.deleteAdmin(adminId);
		return "redirect:adminList";
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/newAdmin", method=RequestMethod.GET)
	public ModelAndView newAdmin() {
		logger.info("Create new admin instance");
		Admin admin = new Admin();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("admin", admin);
		return new ModelAndView("admin/newAdmin", model);
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/saveAdmin", method=RequestMethod.POST)
	public String createAdmin(@ModelAttribute("admin") Admin admin,
			BindingResult result, Model model) {
		logger.info("Save admin instance");
		adminService.saveAdmin(admin);
		return "redirect:adminList";
	}

	
	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
}
