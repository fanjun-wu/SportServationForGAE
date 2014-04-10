package org.groept.cloudMigration.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;














import org.groept.cloudMigration.domain.Court;
import org.groept.cloudMigration.domain.Hall;
import org.groept.cloudMigration.services.CourtService;
import org.groept.cloudMigration.services.HallService;
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
public class HallController  {
	
	private static final Logger logger = LoggerFactory.getLogger(HallController.class);
	

	private CourtService courtService;
	private HallService hallService;
	
	 
	
@RequestMapping(value="/hallList", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("Listing halls.");
		Collection<Hall> halls = hallService.getHalls();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("halls", halls);
		return new ModelAndView("hall/hallList", model);
	}
	
@RequestMapping(value="/getHall", method=RequestMethod.GET)
public ModelAndView fetchHall(@RequestParam("hallId") Long hallId) {
	logger.info("Fetching hall " + hallId);
	Hall hall = hallService.getHall(hallId);
	Map<String,Object>modelAndView = new HashMap<String,Object>();
	modelAndView.put("hall", hall);
	return new ModelAndView("hall/modifyhall", modelAndView);
}
	
	@RequestMapping(value="/newHall", method=RequestMethod.GET)
	public ModelAndView newHall() {
		logger.info("Create new hall instance");
		Hall hall = new Hall();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("hall", hall);
		return new ModelAndView("hall/newHall", model);
	}
	
	@RequestMapping(value="/deleteHall", method=RequestMethod.GET)
	public String deleteHall(@RequestParam("hallId") Long hallId) {
		logger.info("Deleting hall " + hallId);
		hallService.deleteHall(hallId);
		return "redirect:hallList";
	}
	
	@RequestMapping(value="/saveHall", method=RequestMethod.POST)
	public String createHall(@ModelAttribute("hall") Hall hall,
			BindingResult result, Model model) {
		logger.info("Save hall instance");
		hallService.saveHall(hall);
		return "redirect:hallList";
	}
	
	
	@RequestMapping(value="/CreatCourtInHall", method=RequestMethod.GET)
	public ModelAndView CreatCourtInHall(@RequestParam("hallId") Long hallId) {
		
		

		Court court = new Court();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("hallId", hallId);
		model.put("court", court);
		return new ModelAndView("hall/newCourt", model);
	}
	
	@RequestMapping(value="/saveCourtinHall", method=RequestMethod.POST)
	public String createCourtinHall(@RequestParam("hallId") Long hallId,@ModelAttribute("court") Court court,
			BindingResult result, Model model) {
		logger.info("Save court instance");
		
		Hall hall =hallService.getHall(hallId);
		court.setHallName(hall.getName());
		
		
		courtService.saveCourt(court);
		
		
		
		
		return "redirect:courtList";
	}
	
	@RequestMapping(value="/showhallCourtList", method=RequestMethod.GET)
	public ModelAndView listofCourt(@RequestParam("hallId") Long hallId) {
		logger.info("Listing courts.");
		Collection<Court> courts = hallService.listofCourts(hallId);
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("courts", courts);
		return new ModelAndView("hall/showhallCourtList", model);
	}
	
	
	
	@Autowired
	public void setHallService(HallService hallService) {
		this.hallService = hallService;
	}
	
	@Autowired
	public void setCourtService(CourtService courtService) {
		this.courtService = courtService;
	}
	

}
