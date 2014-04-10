package org.groept.cloudMigration.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.groept.cloudMigration.domain.Capability;
import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.services.CapabilityService;
import org.groept.cloudMigration.services.CourtService;
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
public class CourtController {

	private static final Logger logger = LoggerFactory.getLogger(CourtController.class);

	private CourtService courtService;
	private CapabilityService capabilityService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/courtList", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("Listing courts.");
		Collection<Court> courts = courtService.getCourts();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("courts", courts);
		return new ModelAndView("court/courtList", model);
	}
	
	
	
	
	/**
	 * Fetch a court by it's ID.
	 */
	@RequestMapping(value="/getCourt", method=RequestMethod.GET)
	public ModelAndView fetchCourt(@RequestParam("courtId") Long courtId) {
		logger.info("Fetching court " + courtId);
		Court court = courtService.getCourt(courtId);
		Map<String,Object>modelAndView = new HashMap<String,Object>();
		modelAndView.put("court", court);
		return new ModelAndView("court/modifyCourt", modelAndView);
	}
	
	
	/**
	 * Fetch a court by it's ID.
	 */
	@RequestMapping(value="/deleteCourt", method=RequestMethod.GET)
	public String deleteCourt(@RequestParam("courtId") Long courtId) {
		logger.info("Deleting court " + courtId);
		courtService.deleteCourt(courtId);
		return "redirect:courtList";
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/newCourt", method=RequestMethod.GET)
	public ModelAndView newCourt() {
		logger.info("Create new court instance");
		Court court = new Court();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("court", court);
		return new ModelAndView("court/newCourt", model);
	}
	
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/saveCourt", method=RequestMethod.POST)
	public String createCourt(@ModelAttribute("court") Court court,
			BindingResult result, Model model) {
		logger.info("Save court instance");
		courtService.saveCourt(court);
		return "redirect:courtList";
	}

	
	@RequestMapping(value="/addcoutcap", method=RequestMethod.GET)
	public ModelAndView caplist(@RequestParam("courtId") Long courtId) {
		logger.info("Listing capabilities.");
		Collection<Capability> capabilities = capabilityService.getCapabilities();
		/*Court c =courtService.getCourt(courtId);
		
		for(Capability cap: capabilities)
		{
			if(c.getCapabilitiesName().contains(cap.getResource()))
			{
				capabilities.remove(cap);
			}
		}*/
		
		
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("courtId",courtId);
		model.put("capabilities", capabilities);
		return new ModelAndView("court/addcoutcap", model);
	}

	
	@RequestMapping(value="/capaCourtLink", method=RequestMethod.GET)
	public String capaCourtLink(@RequestParam("capabilityId") Long capabilityId,@RequestParam("courtId") Long courtId) {
		
		Court c =courtService.getCourt(courtId);
		Capability cap= capabilityService.getCapability(capabilityId);
		
		c.addCapability(cap);
		cap.addCourt(c);
		
		courtService.saveCourt(c);
		capabilityService.saveCapability(cap);
		
		
		return "redirect:courtList";
	}
	
	@RequestMapping(value="/showCourtCapList", method=RequestMethod.GET)
	public ModelAndView listofCap(@RequestParam("courtId") Long courtId) {
		logger.info("Listing courts.");
		
		Collection<Capability> capabilities = courtService.listofCapabilitys(courtId);
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("capabilities", capabilities);
		return new ModelAndView("court/capabilityList", model);
	}
	
	
	
	
	@Autowired
	public void setCourtService(CourtService courtService) {
		this.courtService = courtService;
	}
	
	@Autowired
	public void setCapabilityService(CapabilityService capabilityService) {
		this.capabilityService = capabilityService;
	}
}
