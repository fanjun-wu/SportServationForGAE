package org.groept.cloudMigration.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.groept.cloudMigration.domain.TimeInterval;
import org.groept.cloudMigration.services.TimeIntervalService;
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
public class TimeIntervalController {
	private static final Logger logger = LoggerFactory.getLogger(TimeIntervalController.class);

	private TimeIntervalService timeIntervalService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/timeIntervalList", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("Listing timeIntervals.");
		Collection<TimeInterval> timeIntervals = timeIntervalService.getTimeIntervals();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("timeIntervals", timeIntervals);
		return new ModelAndView("timeInterval/timeIntervalList", model);
	}
	
	
	
	
	/**
	 * Fetch a timeInterval by it's ID.
	 */
	@RequestMapping(value="/getTimeInterval", method=RequestMethod.GET)
	public ModelAndView fetchTimeInterval(@RequestParam("timeIntervalId") Long timeIntervalId) {
		logger.info("Fetching timeInterval " + timeIntervalId);
		TimeInterval timeInterval = timeIntervalService.getTimeInterval(timeIntervalId);
		Map<String,Object>modelAndView = new HashMap<String,Object>();
		modelAndView.put("timeInterval", timeInterval);
		return new ModelAndView("timeInterval/modifyTimeInterval", modelAndView);
	}
	
	
	/**
	 * Fetch a timeInterval by it's ID.
	 */
	@RequestMapping(value="/deleteTimeInterval", method=RequestMethod.GET)
	public String deleteTimeInterval(@RequestParam("timeIntervalId") Long timeIntervalId) {
		logger.info("Deleting timeInterval " + timeIntervalId);
		timeIntervalService.deleteTimeInterval(timeIntervalId);
		return "redirect:timeIntervalList";
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/newTimeInterval", method=RequestMethod.GET)
	public ModelAndView newTimeInterval() {
		logger.info("Create new timeInterval instance");
		TimeInterval timeInterval = new TimeInterval();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("timeInterval", timeInterval);
		return new ModelAndView("timeInterval/newTimeInterval", model);
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/saveTimeInterval", method=RequestMethod.POST)
	public String createTimeInterval(@ModelAttribute("timeInterval") TimeInterval timeInterval,
			BindingResult result, Model model) {
		logger.info("Save timeInterval instance");
		timeIntervalService.saveTimeInterval(timeInterval);
		return "redirect:timeIntervalList";
	}

	
	@Autowired
	public void setTimeIntervalService(TimeIntervalService timeIntervalService) {
		this.timeIntervalService = timeIntervalService;
	}
	
}
