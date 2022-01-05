package com.fdm.MySoloProject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GYM_Trainer")
public class Trainer {
	@Id
	@GeneratedValue
	private int trainerId;
	
	private String trainerName;
	
	private int contactNumber;

	public Trainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Trainer(String trainerName, int contactNumber) {
		super();
		this.trainerName = trainerName;
		this.contactNumber = contactNumber;
	}

	public int getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public int getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", trainerName=" + trainerName + ", contactNumber=" + contactNumber
				+ "]";
	}
	
	

}
