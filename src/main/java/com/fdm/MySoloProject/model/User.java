package com.fdm.MySoloProject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue
	private int userId;
	
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	
	@OneToMany(mappedBy = "bookedUser")
	private List<Booking> bookings = new ArrayList<>();
	
	@OneToOne
	private MembershipPlans membershipPlan;
	
	public User() {
		super();
	}

	public User(String username, String firstName, String lastName, String password, String email,
			List<Booking> bookings, MembershipPlans membershipPlan) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.bookings = bookings;
		this.membershipPlan = membershipPlan;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public MembershipPlans getMembershipPlan() {
		return membershipPlan;
	}

	public void setMembershipPlan(MembershipPlans membershipPlan) {
		this.membershipPlan = membershipPlan;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", email=" + email + ", bookings=" + bookings
				+ ", membershipPlan=" + membershipPlan + "]";
	}



}
