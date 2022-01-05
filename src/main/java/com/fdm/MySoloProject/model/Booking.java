package com.fdm.MySoloProject.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name="GYM_Bookings")
public class Booking {
	@Id
	@GeneratedValue
	private int bookingId;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dateTime;
	
	@ManyToOne
	private PersonalTraining personalTraining;
	
	@ManyToOne
	private User bookedUser;

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Booking(LocalDateTime dateTime, PersonalTraining personalTraining, User bookedUser) {
		super();
		this.dateTime = dateTime;
		this.personalTraining = personalTraining;
		this.bookedUser = bookedUser;
	}



	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}




	public User getBookedUser() {
		return bookedUser;
	}



	public void setBookedUser(User bookedUser) {
		this.bookedUser = bookedUser;
	}



	public PersonalTraining getPersonalTraining() {
		return personalTraining;
	}



	public void setPersonalTraining(PersonalTraining personalTraining) {
		this.personalTraining = personalTraining;
	}



	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", dateTime=" + dateTime + ", personalTraining=" + personalTraining + ", bookedUser=" + bookedUser + "]";
	}

	
	

	

	
}
