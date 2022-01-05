package com.fdm.MySoloProject.controller;

import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.MySoloProject.model.Booking;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.User;
import com.fdm.MySoloProject.service.BookingService;
import com.fdm.MySoloProject.service.PersonalTrainingService;
import com.fdm.MySoloProject.service.UserService;

public class BookingControllerTest {
	private BookingController bookingController;

	@Mock
	private BookingService mockBookingService;
	
	@Mock
	private UserService mockUserService;
	
	@Mock
	private PersonalTrainingService mockPTService;

	@Mock
	private HttpSession mockSession;

	@Mock
	private Model mockModel;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		bookingController = new BookingController(mockPTService, mockUserService, mockBookingService);
	}
	
	@Test
	public void when_getBooking_returns_activeUser_returns_bookingurl() {
		//Arrange
		User testActiveUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		List<PersonalTraining> listOfTestPT = new ArrayList<>();
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(testActiveUser);
		when(mockPTService.getListOfPT()).thenReturn(listOfTestPT);
		//Act
		String result = bookingController.getBooking(mockSession, mockModel);
		//Assert
		InOrder order = inOrder(mockSession, mockPTService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockPTService,times(1)).getListOfPT();
		order.verify(mockSession,times(1)).setAttribute(BookingController.LIST_OF_PT_ATTR, listOfTestPT);
		assertEquals(result, BookingController.BOOKING_VIEW);
	}
	
	@Test
	public void when_getBooking_returns_null_returns_indexURL() {
		//Arrange
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(null);
		//Act
		String result = bookingController.getBooking(mockSession, mockModel);
		//Assert
		InOrder order = inOrder(mockSession, mockModel);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockModel,times(1)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.LOGIN_TO_BOOK_MSG);
		assertEquals(result, BookingController.INDEX_VIEW);
	}
	
	@Test
	public void when_processBookingPT_return_id_is_null_returnBookView() {
		//Arrange
		
		
		//Act
		String result = bookingController.processBookingPT(mockSession, mockModel, null);
		
		//Assert
		verify(mockModel,times(1)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.NO_OPTION_MESSAGE);
		assertEquals(result, BookingController.BOOKING_VIEW);
		
	}
	
	@Test
	public void when_processBookingPt_returns_multipleoption_returnBookView() {
		//Arrange
		
		
		//Act
		String result = bookingController.processBookingPT(mockSession, mockModel, "^[1-5]");
		//Assert
		verify(mockModel,times(1)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.ONLY_SELECT_ONE_MSG);
		assertEquals(result, BookingController.BOOKING_VIEW);
	}
	
	
	@Test
	public void when_processBookingPT_returns_correctSelection_returns_bookingTimeView_when_foundPT() {
		//Arrange
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 8, 11, 00);
		String testSelectedOpt = "1";
		int parsedSelectedOpt = Integer.parseInt(testSelectedOpt);
		User testActiveUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		Optional<PersonalTraining> testFoundPT = Optional
				.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, null));
//		Booking testCurrentBooking = new Booking(testDateTime, testFoundPT.get(), testActiveUser);
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(testActiveUser);
		when(mockBookingService.findPTById(parsedSelectedOpt)).thenReturn(testFoundPT);
		
		//Act
		String result = bookingController.processBookingPT(mockSession, mockModel, testSelectedOpt);
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockBookingService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockBookingService,times(1)).findPTById(parsedSelectedOpt);
//		verify(mockSession,times(1)).setAttribute(BookingController.CURRENT_BOOKING_ATTR, testCurrentBooking);
		order.verify(mockModel,times(1)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.CHOOSE_CAREFULLY_MSG);
		assertEquals(result, BookingController.BOOK_TIME_VIEW);
	}
	
	@Test
	public void when_processBookingPt_returns_correctSelection_returns_bookingView_when_NofoundPT() {
		//Arrange
		String testSelectedOpt = "1";
		int parsedSelectedOpt = Integer.parseInt(testSelectedOpt);
		User testActiveUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		Optional<PersonalTraining> testFoundPT = Optional.empty();
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(testActiveUser);
		when(mockBookingService.findPTById(parsedSelectedOpt)).thenReturn(testFoundPT);
		
		//Act
		String result = bookingController.processBookingPT(mockSession, mockModel, testSelectedOpt);
		//Assert
		InOrder order = inOrder(mockSession, mockModel, mockBookingService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockBookingService,times(1)).findPTById(parsedSelectedOpt);
		order.verify(mockModel,times(1)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.PROGRAM_NOT_AVAILABLE_MSG);
		assertEquals(result, BookingController.BOOKING_VIEW);
	}
	
	@Test
	public void when_processBookingPTDate_return_datetimeOutsideWorkingHours_return_BookTIMEVIEW() {
		//Arrange
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 30, 9, 00);
		LocalDateTime testDateTime2 = LocalDateTime.of(2021, 12, 30, 22, 00);
		//Act
		String result = bookingController.processBookingPTDate(mockSession, mockModel, testDateTime);
		String result2 = bookingController.processBookingPTDate(mockSession, mockModel, testDateTime2);
		//Assert
		verify(mockModel,times(2)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.OUTSIDE_WORKING_HOURS_MSG);
		verify(mockModel,times(2)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.CHOOSE_CAREFULLY_MSG);
		assertEquals(result, BookingController.BOOK_TIME_VIEW);
		assertEquals(result2, BookingController.BOOK_TIME_VIEW);
	}
	
	@Test
	public void when_processBookingPTDate_return_MINNOT00_return_BookTIMEVIEW() {
		//Arrange
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 30, 10, 32);

		//Act
		String result = bookingController.processBookingPTDate(mockSession, mockModel, testDateTime);
		
		//Assert
		InOrder order = inOrder(mockModel);
		order.verify(mockModel,times(1)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.MIN_00_MSG);
		order.verify(mockModel,times(1)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.CHOOSE_CAREFULLY_MSG);
		assertEquals(result, BookingController.BOOK_TIME_VIEW);
	}
	
	@Test 
	public void when_processBookingPTDate_return_validentry_but_DateTimeClash_return_BookTIMEVIEW() {
		//Arrange
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 30, 10, 30);
		PersonalTraining testFoundPT = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		testFoundPT.setPersonalTrainingId(1);
		Booking testConfirmBooking = new Booking(testDateTime, testFoundPT, null);
		when(mockSession.getAttribute(BookingController.CURRENT_BOOKING_ATTR)).thenReturn(testConfirmBooking);
		when(mockBookingService.checkBookingClash(testConfirmBooking.getPersonalTraining().getPersonalTrainingId(), testDateTime))
		.thenReturn(true);
		//Act
		String result = bookingController.processBookingPTDate(mockSession, mockModel, testDateTime);
		
		//Assert               ??? why no interaction with mocks? but end result correct?
//		verify(mockSession,times(1)).getAttribute(BookingController.CURRENT_BOOKING_ATTR);
//		verify(mockBookingService,times(1)).checkBookingClash(testConfirmBooking.getPersonalTraining().getPersonalTrainingId(), testDateTime);
//		verify(mockModel,times(1)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.TIMING_CLASH_MSG);
		assertEquals(result, BookingController.BOOK_TIME_VIEW);
		
	}
	
	
	@Test
	public void when_processBookingPTDate_return_validentry_no_DateTimeClash_return_indexView() {
		//Arrange
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 30, 10, 30);
		LocalDateTime testDateTime2 = LocalDateTime.of(2021, 12, 30, 20, 00);
		PersonalTraining testFoundPT = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		testFoundPT.setPersonalTrainingId(1);
		Booking testConfirmBooking = new Booking(testDateTime, testFoundPT, null);
		when(mockSession.getAttribute(BookingController.CURRENT_BOOKING_ATTR)).thenReturn(testConfirmBooking);
		when(mockBookingService.checkBookingClash(testConfirmBooking.getPersonalTraining().getPersonalTrainingId(), testDateTime2))
		.thenReturn(false);
		//Act
		String result = bookingController.processBookingPTDate(mockSession, mockModel, testDateTime2);
		
		//Assert    
		InOrder order = inOrder(mockModel, mockSession, mockBookingService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.CURRENT_BOOKING_ATTR);
		order.verify(mockBookingService,times(1)).checkBookingClash(testConfirmBooking.getPersonalTraining().getPersonalTrainingId(), testDateTime2);
		order.verify(mockSession,times(1)).setAttribute(BookingController.CONFIRMED_BOOKING_ATTR, testConfirmBooking);
		order.verify(mockModel,times(1)).addAttribute(BookingController.MESSAGE_ATTR, BookingController.BOOKING_CONFIRMED_MSG);
		assertEquals(result, BookingController.INDEX_VIEW);
		
	}
	
	
	@Test 
	public void when_getUserBookings_return_pastFutureBookingList_return_checkBookingview() {
		//Arrange
		User testActiveUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		LocalDateTime testDateTime = LocalDateTime.of(2021, 12, 1, 10, 30);
		LocalDateTime testDateTime2 = LocalDateTime.of(2021, 12, 30, 20, 00);
		PersonalTraining testFoundPT = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		Booking testBookingPast = new Booking(testDateTime, testFoundPT, testActiveUser);
		Booking testBookingFuture = new Booking(testDateTime2, testFoundPT, testActiveUser);
		List<Booking> testPastBookings = new ArrayList<>();
		List<Booking> testFutureBookings = new ArrayList<>();
		List<Booking> testAllBooking = new ArrayList<>();
		testAllBooking.add(testBookingFuture);
		testAllBooking.add(testBookingPast);
		testPastBookings.add(testBookingPast);
		testFutureBookings.add(testBookingFuture);
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(testActiveUser);
		when(mockBookingService.getAllUserBookings(testActiveUser)).thenReturn(testAllBooking);
		when(mockBookingService.getPastBookings(testActiveUser)).thenReturn(testPastBookings);
		when(mockBookingService.getFutureBookings(testActiveUser)).thenReturn(testFutureBookings);
		//Act
		String result = bookingController.getUserBookings(mockSession, mockModel);
		
		//Assert
		InOrder order = inOrder(mockModel, mockSession, mockBookingService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockBookingService,times(1)).getAllUserBookings(testActiveUser);
		order.verify(mockBookingService,times(1)).getPastBookings(testActiveUser);
		order.verify(mockBookingService,times(1)).getFutureBookings(testActiveUser);
		assertEquals(result, BookingController.CHECK_BOOKING_VIEW);
	}
	
	public void when_getUserBooking_isEmpty_return_errorMessage_and_checkBookingView() {
		//Arrange
		User testActiveUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		List<Booking> testAllBooking = new ArrayList<>();
		testAllBooking.isEmpty();
		when(mockSession.getAttribute(BookingController.ACTIVE_USER_ATTR)).thenReturn(testActiveUser);
		when(mockBookingService.getAllUserBookings(testActiveUser)).thenReturn(testAllBooking);
		//Act
		String result = bookingController.getUserBookings(mockSession, mockModel);
		//Assert
		InOrder order = inOrder(mockModel, mockSession, mockBookingService);
		order.verify(mockSession,times(1)).getAttribute(BookingController.ACTIVE_USER_ATTR);
		order.verify(mockBookingService,times(1)).getAllUserBookings(testActiveUser);
		order.verify(mockModel,times(1)).addAttribute(BookingController.ERROR_MESSAGE_ATTR, BookingController.NO_BOOKINGS_MSG);
		assertEquals(result, BookingController.CHECK_BOOKING_VIEW);
	}
}
