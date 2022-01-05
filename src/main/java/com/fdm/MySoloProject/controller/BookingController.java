package com.fdm.MySoloProject.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.MySoloProject.model.Booking;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.User;
import com.fdm.MySoloProject.service.BookingService;
import com.fdm.MySoloProject.service.PersonalTrainingService;
import com.fdm.MySoloProject.service.UserService;

@Controller
public class BookingController {

	static final String CHECK_BOOKING_VIEW = "checkBooking";
	static final String FUTURE_BOOKINGS_ATTR = "futureBookings";
	static final String PAST_BOOKINGS_ATTR = "pastBookings";
	static final String NO_BOOKINGS_MSG = "You don't have any bookings";
	static final String CHECK_BOOKING_URL = "/getCheckBooking";
	static final String BOOKING_CONFIRMED_MSG = "Your booking have been confirmed. Please arrive earlier at the gym to make your payment at the counter";
	static final String CONFIRMED_BOOKING_ATTR = "confirmedBooking";
	static final String TIMING_CLASH_MSG = "Timing not available, please select another timing";
	static final String MIN_00_MSG = "Please select only 00 for minutes. Thank you";
	static final String OUTSIDE_WORKING_HOURS_MSG = "Hello! Trainer needs to sleep!";
	static final String PROCESS_BOOKING_TIME_URL = "/processBookingTime";
	static final String BOOK_TIME_VIEW = "bookTime";
	static final String CHOOSE_CAREFULLY_MSG = "Please choose Date and Time carefully as you won't be able to cancel";
	static final String CURRENT_BOOKING_ATTR = "currentBooking";
	static final String PROGRAM_NOT_AVAILABLE_MSG = "That Program is not available, please try another one";
	static final String ONLY_SELECT_ONE_MSG = "Please only select One Session!";
	static final String NO_OPTION_MESSAGE = "You have not select any option!";
	static final String MESSAGE_ATTR = "message";
	static final String PROCESS_PT_URL = "/processPersonalTraining";
	static final String INDEX_VIEW = "index";
	static final String LOGIN_TO_BOOK_MSG = "Please login to Book PT Session";
	static final String ERROR_MESSAGE_ATTR = "errorMessage";
	static final String LIST_OF_PT_ATTR = "listOfPT";
	static final String ACTIVE_USER_ATTR = "activeUser";
	static final String BOOKING_VIEW = "booking";
	static final String GET_BOOKING_URL = "/getBooking";
	
	
	
	
	private PersonalTrainingService pTService;
	private BookingService bookingService;
	private UserService userService;
	private DateTimeFormatter timeFormat24H = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
	private Log log = LogFactory.getLog(BookingController.class);

	@Autowired
	public BookingController(PersonalTrainingService pTService, UserService userService,
			BookingService bookingService) {
		super();
		this.pTService = pTService;
		this.bookingService = bookingService;
		this.userService = userService;
	}



	/**
	 * send loggined in user to booking page when they click on booking,
	 * 
	 * @param session hold list of PT session for user to choose with timing
	 * @param model
	 * @return
	 */
	@GetMapping(value = GET_BOOKING_URL)
	public String getBooking(HttpSession session, Model model) {
		if (session.getAttribute(ACTIVE_USER_ATTR) != null) {
			List<PersonalTraining> listOfPT = pTService.getListOfPT();
			session.setAttribute(LIST_OF_PT_ATTR, listOfPT);
			return BOOKING_VIEW;
		} else {
			model.addAttribute(ERROR_MESSAGE_ATTR, LOGIN_TO_BOOK_MSG);
			return INDEX_VIEW;
		}
	}
	
	/**
	 * process booking.jsp . check if user checked more than 1.
	 * @param session save entry to currentbookingsession for user to proceed before saving
	 * @param model
	 * @param selectedPersonalTrainingId
	 * @return
	 */
	@PostMapping(value=PROCESS_PT_URL)
	public String processBookingPT(HttpSession session, Model model, @RequestParam String selectedPersonalTrainingId) {
		if (selectedPersonalTrainingId == null) {
			model.addAttribute(MESSAGE_ATTR, NO_OPTION_MESSAGE);
			return BOOKING_VIEW;
		}
		if (!selectedPersonalTrainingId.matches("[1-5]")) {
			model.addAttribute(ERROR_MESSAGE_ATTR, ONLY_SELECT_ONE_MSG);
			return BOOKING_VIEW;
		}		
		int personalTrainingId = Integer.parseInt(selectedPersonalTrainingId);
		User currentUser = (User) session.getAttribute(ACTIVE_USER_ATTR);
		Booking currentBooking = new Booking();
		currentBooking.setBookedUser(currentUser);
		Optional<PersonalTraining> foundPT = bookingService.findPTById(personalTrainingId);
		if (foundPT.isEmpty()) {
			model.addAttribute(MESSAGE_ATTR, PROGRAM_NOT_AVAILABLE_MSG);
			return BOOKING_VIEW;
		}
		currentBooking.setPersonalTraining(foundPT.get());
		session.setAttribute(CURRENT_BOOKING_ATTR, currentBooking);
		model.addAttribute(MESSAGE_ATTR, CHOOSE_CAREFULLY_MSG);
		return BOOK_TIME_VIEW;
	}

	/**
	 * process bookTime.jsp dateTime won't be null. check for working hours, 1hr intervals, dateTime clashes and backdate
	 * save valid entries to database
	 * @param session set confirmed booking and string date time
	 * @param model messages and errormessages
	 * @param dateTime
	 * @return
	 */
	@PostMapping(value = PROCESS_BOOKING_TIME_URL)
	public String processBookingPTDate(HttpSession session, Model model, 
			@RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
		LocalTime startTime = LocalTime.of(10, 00);
		LocalTime endTime = LocalTime.of(21, 00);
		LocalDate todaysDate = LocalDate.now();
		if (dateTime.toLocalTime().isBefore(startTime) || dateTime.toLocalTime().isAfter(endTime)) {
			model.addAttribute(ERROR_MESSAGE_ATTR, OUTSIDE_WORKING_HOURS_MSG);
			model.addAttribute(MESSAGE_ATTR, CHOOSE_CAREFULLY_MSG);
			return BOOK_TIME_VIEW;
		}
//		if (dateTime.toLocalDate().isBefore(todaysDate)) {       //removed for demooooooooooooooooooooooood
//			model.addAttribute(ERROR_MESSAGE_ATTR, "Hi, you cannot choose a date before today's date!");
//			model.addAttribute(MESSAGE_ATTR, "Please choose Date and Time carefully as you won't be able to cancel");
//			return BOOK_TIME_VIEW;
//		}
		if (dateTime.getMinute() != 00) {
			model.addAttribute(ERROR_MESSAGE_ATTR, MIN_00_MSG);
			model.addAttribute(MESSAGE_ATTR, CHOOSE_CAREFULLY_MSG);
			return BOOK_TIME_VIEW;
		}
		Booking confirmedBooking = (Booking) session.getAttribute(CURRENT_BOOKING_ATTR);
//		String StringDateTime = dateTime.format(timeFormat24H);
		if (bookingService.checkBookingClash(confirmedBooking.getPersonalTraining().getPersonalTrainingId(), dateTime)) {
			model.addAttribute(ERROR_MESSAGE_ATTR, TIMING_CLASH_MSG);
			return BOOK_TIME_VIEW;
		}
		confirmedBooking.setDateTime(dateTime); // set time and date of Pt
		bookingService.addBooking(confirmedBooking); // save to DB
		session.setAttribute(CONFIRMED_BOOKING_ATTR, confirmedBooking); // set to session
//		session.setAttribute("StringDateTime", StringDateTime);
		model.addAttribute(MESSAGE_ATTR,
				BOOKING_CONFIRMED_MSG);
		return INDEX_VIEW;
	}
	
	
/***
 * show user past and upcoming bookings, include all important details
 * @param session
 * @param model
 * @return
 */
	@GetMapping(value = CHECK_BOOKING_URL)
	public String getUserBookings(HttpSession session, Model model) {
		User activeUser = (User) session.getAttribute(ACTIVE_USER_ATTR);
		List<Booking> UserBookings = bookingService.getAllUserBookings(activeUser);
		if (UserBookings.isEmpty()) {
			model.addAttribute(ERROR_MESSAGE_ATTR, NO_BOOKINGS_MSG);
		} else {
			List<Booking> pastBookings = bookingService.getPastBookings(activeUser);
			List<Booking> futureBookings = bookingService.getFutureBookings(activeUser);
			
			model.addAttribute(PAST_BOOKINGS_ATTR, pastBookings);
			model.addAttribute(FUTURE_BOOKINGS_ATTR, futureBookings);
		}
		return CHECK_BOOKING_VIEW;
	}
	

}
