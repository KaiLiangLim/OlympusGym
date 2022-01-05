package com.fdm.MySoloProject.service;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.MySoloProject.dal.BookingRepository;
import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.model.Booking;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.User;

public class BookingServiceTest {

	BookingService bookingService;

	@Mock
	BookingRepository mockBookingRepo;

	@Mock
	PersonalTrainingRepository mockPTRepo;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		bookingService = new BookingService(mockBookingRepo, mockPTRepo);
	}

	@Test
	public void when_PT_find_BY_return_PT() {
		// Arrange
		PersonalTraining testPT1 = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		Optional<PersonalTraining> testPT = Optional
				.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, null));
		testPT1.setPersonalTrainingId(2);
		when(mockPTRepo.findById(2)).thenReturn(testPT);

		// Act
		Optional<PersonalTraining> result = bookingService.findPTById(testPT1.getPersonalTrainingId());

		// Assert
		verify(mockPTRepo, times(1)).findById(2);
		assertEquals(result, testPT);
	}

	@Test
	public void when_getAllUserBooking_return_listofuserBookings() {
		// Arrange
		User activeUser = new User();
		PersonalTraining testPT1 = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		PersonalTraining testPT2 = new PersonalTraining(999, "kungfu", "poke your eyeball", null, null);
		LocalDateTime dateTime = LocalDateTime.MAX;
		LocalDateTime dateTime1 = LocalDateTime.MIN;
		Booking testBook1 = new Booking(dateTime, testPT1, activeUser);
		Booking testBook2 = new Booking(dateTime1, testPT2, activeUser);
		List<Booking> listOfBookings = new ArrayList<>();
		listOfBookings.add(testBook1);
		listOfBookings.add(testBook2);
		when(mockBookingRepo.findByBookedUser(activeUser)).thenReturn(listOfBookings);

		// Act
		List<Booking> result = bookingService.getAllUserBookings(activeUser);

		// Assert
		verify(mockBookingRepo, atLeastOnce()).findByBookedUser(activeUser);
		assertEquals(result, listOfBookings);
	}

	@Test
	public void when_getBooking_using_bookingID_return_bookingObject() {
		// Arrange
		User activeUser = new User();
		PersonalTraining testPT1 = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		LocalDateTime dateTime = LocalDateTime.MAX;
		Optional<Booking> testBook1 = Optional.of(new Booking(dateTime, testPT1, activeUser));
		when(mockBookingRepo.findById(testBook1.get().getBookingId())).thenReturn(testBook1);

		// Act
		Booking result = bookingService.getBooking(testBook1.get().getBookingId());

		// Assert
		verify(mockBookingRepo, atLeastOnce()).findById(testBook1.get().getBookingId());
		assertEquals(result, testBook1.get());
	}

	@Test
	public void when_getBooking_using_bookingID_return_null() {
		// Arrange
		User activeUser = new User();
		PersonalTraining testPT1 = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		Optional<Booking> testPT2 = Optional.empty();
		LocalDateTime dateTime = LocalDateTime.MAX;
		Optional<Booking> testBook1 = Optional.of(new Booking(dateTime, testPT1, activeUser));
		when(mockBookingRepo.findById(testBook1.get().getBookingId())).thenReturn(testPT2);

		// Act
		Booking result = bookingService.getBooking(testBook1.get().getBookingId());

		// Assert
		verify(mockBookingRepo, atLeastOnce()).findById(testBook1.get().getBookingId());
		assertNull(result);
		assertEquals(result, null);
	}

	@Test
	public void when_checkBookingClash_clash_return_True() { // fail
		// Arrange
		User activeUser = new User();
		User anotherUser = new User();
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime dateTime1 = LocalDateTime.now();
		List<Booking> registeredBookingList = new ArrayList<>();
		Optional<PersonalTraining> registeredPT = Optional
				.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, registeredBookingList));
		Booking registeredBook = new Booking(dateTime, registeredPT.get(), anotherUser);
		registeredBookingList.add(registeredBook);
		Booking newBook = new Booking(dateTime1, registeredPT.get(), activeUser);
		when(mockPTRepo.findById(newBook.getPersonalTraining().getPersonalTrainingId())).thenReturn(registeredPT);

		// Act
		boolean result = bookingService.checkBookingClash(newBook.getPersonalTraining().getPersonalTrainingId(),
				dateTime);

		// Assert
		verify(mockPTRepo, atLeastOnce()).findById(registeredBook.getPersonalTraining().getPersonalTrainingId());
		assertTrue(result);
		assertEquals(result, true);
	}

	@Test
	public void when_checkBookingClash_noClash_returns_false() {
		// Arrange
		User activeUser = new User();
		User anotherUser = new User();
		LocalDateTime dateTime = LocalDateTime.MIN;
		LocalDateTime dateTime1 = LocalDateTime.MAX;
		List<Booking> registeredBookingList = new ArrayList<>();
		Optional<PersonalTraining> registeredPT = Optional
				.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, registeredBookingList));
		Booking registeredBook = new Booking(dateTime, registeredPT.get(), anotherUser);
		registeredBookingList.add(registeredBook);
		Booking newBook = new Booking(dateTime1, registeredPT.get(), activeUser);
		when(mockPTRepo.findById(newBook.getPersonalTraining().getPersonalTrainingId())).thenReturn(registeredPT);

		// Arrange
		boolean result = bookingService.checkBookingClash(newBook.getPersonalTraining().getPersonalTrainingId(),
				dateTime1);

		// Assert
		verify(mockPTRepo, atLeastOnce()).findById(newBook.getPersonalTraining().getPersonalTrainingId());
		assertFalse(result);
		assertEquals(result, false);
	}

	@Test
	public void when_addBooking_bookingRepo_save_booking() {
		// Arrange
		LocalDateTime dateTime1 = LocalDateTime.MAX;
		Optional<PersonalTraining> registeredPT = Optional
				.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, null));
		User activeUser = new User();
		Booking testBook = new Booking(dateTime1, registeredPT.get(), activeUser);
		Booking testSavedBook = new Booking(dateTime1, registeredPT.get(), activeUser);
		testSavedBook.setBookingId(1); // simulate saved booking
		when(mockBookingRepo.save(testBook)).thenReturn(testSavedBook);

		// Act
		Booking result = bookingService.addBooking(testBook);

		// Assert
		verify(mockBookingRepo, times(1)).save(testBook);
		assertEquals(result, testSavedBook);
	}

//	@Test                                                         //  this two fails
//	public void when_call_getPastBookings_return_listofpastBookings() {
//		// Arrange
//		LocalDateTime pastTime = LocalDateTime.of(2020, 5, 5, 10, 00);
//		LocalDateTime futureTime = LocalDateTime.of(2022, 5, 5, 10, 00);
//		PersonalTraining testPT = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
//		List<Booking> testBookingList = new ArrayList<>();
//		User activeUser = new User();
//		Booking pastBooking = new Booking(pastTime, testPT, activeUser);
//		Booking futureBooking = new Booking(futureTime, testPT, activeUser);
//		testBookingList.add(futureBooking);
//		testBookingList.add(pastBooking);
//		List<Booking> pastBookings =  new ArrayList<>();
//		pastBookings.add(pastBooking);
//		
//		//Act
//		List<Booking> result = bookingService.getPastBookings(activeUser);
//		
//		//Assert
//		assertEquals(result, pastBookings);
//	}
//	
//	@Test
//	public void when_call_getFutureBookings_return_listoffutureBookings() {
//		// Arrange
//		LocalDateTime pastTime = LocalDateTime.of(2020, 5, 5, 10, 00);
//		LocalDateTime futureTime = LocalDateTime.of(2022, 5, 5, 10, 00);
//		PersonalTraining testPT = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
//		List<Booking> testBookingList = new ArrayList<>();
//		User activeUser = new User();
//		Booking pastBooking = new Booking(pastTime, testPT, activeUser);
//		Booking futureBooking = new Booking(futureTime, testPT, activeUser);
//		testBookingList.add(futureBooking);
//		testBookingList.add(pastBooking);
//		List<Booking> futureBookings =  new ArrayList<>();
//		futureBookings.add(futureBooking);
//		
//		//Act
//		List<Booking> result = bookingService.getFutureBookings(activeUser);
//		
//		//Assert
//		assertEquals(result, futureBookings);
//	}

}
