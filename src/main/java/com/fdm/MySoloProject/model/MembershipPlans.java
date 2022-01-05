package com.fdm.MySoloProject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Gym_MembershipPlan")
public class MembershipPlans {

	@Id
	@GeneratedValue
	private int planId;
	
	private int duration;
	
	private double price;
	
	private int userId;
	
	@OneToOne
	private User user;



	public MembershipPlans() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MembershipPlans(int planId, int duration, double price) {
		super();
		this.planId = planId;
		this.duration = duration;
		this.price = price;
	}



	public int getPlanId() {
		return planId;
	}



	public void setPlanId(int planId) {
		this.planId = planId;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	@Override
	public String toString() {
		return "MembershipPlans [planId=" + planId + ", duration=" + duration + ", price=" + price + ", userId="
				+ userId + "]";
	}
	
	
	
}
