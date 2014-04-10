package org.groept.cloudMigration.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.groept.cloudMigration.domain.Reservation;
import org.groept.cloudMigration.services.ReservationService;
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
public class ReservationController {

	
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	private ReservationService reservationService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/reservationList", method=RequestMethod.GET)
	public ModelAndView list() {
		logger.info("Listing reservations.");
		Collection<Reservation> reservations = reservationService.getReservations();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("reservations", reservations);
		return new ModelAndView("reservation/reservationList", model);
	}
	
	
	
	
	/**
	 * Fetch a reservation by it's ID.
	 */
	@RequestMapping(value="/getReservation", method=RequestMethod.GET)
	public ModelAndView fetchReservation(@RequestParam("reservationId") Long reservationId) {
		logger.info("Fetching reservation " + reservationId);
		Reservation reservation = reservationService.getReservation(reservationId);
		Map<String,Object>modelAndView = new HashMap<String,Object>();
		modelAndView.put("reservation", reservation);
		return new ModelAndView("reservation/modifyReservation", modelAndView);
	}
	
	
	/**
	 * Fetch a reservation by it's ID.
	 */
	@RequestMapping(value="/deleteReservation", method=RequestMethod.GET)
	public String deleteReservation(@RequestParam("reservationId") Long reservationId) {
		logger.info("Deleting reservation " + reservationId);
		reservationService.deleteReservation(reservationId);
		return "redirect:reservationList";
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/newReservation", method=RequestMethod.GET)
	public ModelAndView newReservation() {
		logger.info("Create new reservation instance");
		Reservation reservation = new Reservation();
		Map<String,Object>model = new HashMap<String,Object>();
		model.put("reservation", reservation);
		return new ModelAndView("reservation/newReservation", model);
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/saveReservation", method=RequestMethod.POST)
	public String createReservation(@ModelAttribute("reservation") Reservation reservation,
			BindingResult result, Model model) {
		logger.info("Save reservation instance");
		reservationService.saveReservation(reservation);
		return "redirect:reservationList";
	}

	
	@Autowired
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
}
