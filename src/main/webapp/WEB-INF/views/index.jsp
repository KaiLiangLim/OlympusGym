<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<title></title>
</head>
<body>
	<section class="header">
		<nav>
			<a href="getHome"><h1>Olympus Athletic Club</h1><img src="resources/img/logo.jpg" height="120"
				width="250"></a>
			<c:choose>
				<c:when test="${not empty sessionScope.activeUser }">
					<div class="nav-links" id="navLinks">
						<i class="fa fa-times" onclick="hideMenu()"></i>
						<ul>
							<li><a href="getHome">Home</a></li>
							<li><a href="getAboutUs">About Us</a></li>
							<li><a href="getProfile">My Profile</a></li>
							<li><a href="userUpdate">Update My Profile</a></li>
							<li><a href="deleteUser">Delete Account</a></li>
							<li><a href="getMembershipPlanPage">Membership Plans</a></li>
							<li><a href="getPT">Personal Training</a></li>
							<li><a href="getBooking">Make Booking</a>
							<li><a href="getCheckBooking">Check Booking</a>
							<li><a href="getLogout">Log out</a></li>
						</ul>
					</div>
					<i class="fa fa-bars" onclick="showMenu()"></i>
				</c:when>
				<c:otherwise>
					<div class="nav-links" id="navLinks">
						<i class="fa fa-times" onclick="hideMenu()"></i>
						<ul>
							<li><a href="getHome">Home</a></li>
							<li><a href="getAboutUs">About Us</a></li>
							<li><a href="getMembershipPlanPage">Membership Plans</a></li>
							<li><a href="getPT">Personal Training</a></li>
							<li><a href="registerUser">Register</a></li>
							<li><a href="userLogin">Login</a></li>
						</ul>
					</div>
					<i class="fa fa-bars" onclick="showMenu()"></i>
				</c:otherwise>
			</c:choose>
		</nav>
	</section>
	<div class="text-box">
		<h1>Olympus Athletic Club</h1>
		<p>
	<c:if test="${ not empty errorMessage }">
		<p>${ errorMessage }</p>
	</c:if>
	<c:if test="${ not empty message }">
		<p>${ message }</p>
	</c:if>
	</p>
	<a href="getAboutUs" class="hero-btn">Click Here To Learn More</a>
</div>
</body>
</html>