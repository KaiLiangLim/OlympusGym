package com.fdm.MySoloProject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.MySoloProject.dal.BookingRepository;
import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.model.Booking;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.User;

@Service
public class BookingService {

	private BookingRepository bookingRepo;
	private PersonalTrainingRepository pTRepo;

	@Autowired
	public BookingService(BookingRepository bookingRepo, PersonalTrainingRepository pTRepo) {
		super();
		this.bookingRepo = bookingRepo;
		this.pTRepo = pTRepo;
	}

	public Optional<PersonalTraining> findPTById(int PTId) {
		return pTRepo.findById(PTId);
	}

	public List<Booking> getAllUserBookings(User activeUser) {
		List<Booking> listOfBookings = bookingRepo.findByBookedUser(activeUser);
		return listOfBookings;
	}

	public Booking getBooking(int bookingId) {
		Optional<Booking> found = bookingRepo.findById(bookingId);
		if (bookingRepo.findById(bookingId).isPresent()) {
			return found.get();
		} else {
			return null;
		}
	}

	/**
	 * check if there is already booking for the user at the specific slot
	 * 
	 * @param pTID
	 * @param ptDateTime
	 * @return
	 */
	public boolean checkBookingClash(int pTID, LocalDateTime ptDateTime) {
		Optional<PersonalTraining> foundPersonalTraining = pTRepo.findById(pTID);
		if (foundPersonalTraining.isPresent()) {
			for (Booking pTBooking : foundPersonalTraining.get().getBookings()) {
				if (pTBooking.getDateTime().equals(ptDateTime)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * add selected pTSession to booking
	 * 
	 * @param sessionToBeAdded
	 * @return
	 */
	public Booking addBooking(Booking sessionToBeAdded) {
		return bookingRepo.save(sessionToBeAdded);
	}

	public List<Booking> getPastBookings(User activeUser) {
		List<Booking> UserBookings = getAllUserBookings(activeUser);
		List<Booking> pastBookings = new ArrayList<>();
		for (Booking booking : UserBookings) {
			if (booking.getDateTime().isBefore(LocalDateTime.now())) {
				pastBookings.add(booking);
			}
		}
		return pastBookings;
	}

	public List<Booking> getFutureBookings(User activeUser) {
		List<Booking> UserBookings = getAllUserBookings(activeUser);
		List<Booking> futureBookings = new ArrayList<>();
		for (Booking booking : UserBookings) {
			if (booking.getDateTime().isAfter(LocalDateTime.now())) {
				futureBookings.add(booking);
			}
		}
		return futureBookings;
	}
	

}
