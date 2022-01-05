package com.fdm.MySoloProject.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Gym_PersonalTraining")
public class PersonalTraining {

	@Id
	@GeneratedValue
	private int personalTrainingId;
	private int price;
	private String categoryName;
	private String description;
	

	
	@OneToOne
	private Trainer trainer;
	
	@OneToMany
	private List<Booking> bookings = new ArrayList<>();

	public PersonalTraining() {
		super();
		// TODO Auto-generated constructor stub
	}



	public PersonalTraining(int price, String categoryName, String description, Trainer trainer,
			List<Booking> bookings) {
		super();
		this.price = price;
		this.categoryName = categoryName;
		this.description = description;
		this.trainer = trainer;
		this.bookings = bookings;
	}




	public int getPersonalTrainingId() {
		return personalTrainingId;
	}



	public void setPersonalTrainingId(int personalTrainingId) {
		this.personalTrainingId = personalTrainingId;
	}



	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}



	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public Trainer getTrainer() {
		return trainer;
	}



	public void setTrainers(Trainer trainer) {
		this.trainer = trainer;
	}



	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}



	@Override
	public String toString() {
		return "PersonalTraining [personalTrainingId=" + personalTrainingId + ", price=" + price + ", categoryName="
				+ categoryName + ", description=" + description + ", trainer=" + trainer + ", bookings=" + bookings
				+ "]";
	}







	
}
