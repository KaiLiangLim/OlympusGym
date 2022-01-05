package com.fdm.MySoloProject.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fdm.MySoloProject.model.User;
import com.fdm.MySoloProject.service.UserService;

/***
 * 
 * @author kaili
 *
 */

@Controller
public class MainController {

	static final String USER_DELETED_MSG = "User Deleted Sucessfully!";

	static final String UPDATED_USER_PASS_MSG = "Username and Password Updated!";

	static final String WRONG_USER_PASS_MSG = "Username or Password is incorrect, please try again";

	static final String REG_SUCCESS_MSG = "Registration Sucessful!";

	static final String LOGIN_TO_VIEW_PROFILE_MSG = "Please Log In to View Profile";

	static final String ERROR_MESSAGE_ATTR = "errorMessage";

	static final String LOGIN_SUCCESSFUL_MESSAGE = "Login Successful!";

	static final String MESSAGE_ATTR = "message";

	static final String BIND_TO_REMOVE_ATTR = "bindToRemove";

	static final String BIND_NEW_UPDATE_USER_ATTR = "bindNewUser";

	static final String USER_ATTR = "user";

	static final String BIND_USER_FORM_ATTR = "bindUser";

	static final String ACTIVE_USER_ATTR = "activeUser";

	static final String PROCESS_DELETE_USER_VIEW = "processDeleteUser";

	static final String UPDATE_USER_INFO_URL = "/updateUserInfo";

	static final String PROCESS_LOGIN_URL = "/processLogin";

	static final String PROCESS_USER_URL = "/processUser";

	static final String USER_PROFILE_VIEW = "userProfile";

	static final String GET_PROFILE_URL = "/getProfile";

	static final String GET_LOGOUT_URL = "/getLogout";

	static final String DELETE_USER_VIEW = "deleteUser";

	static final String DELETE_USER_URL = "/deleteUser";

	static final String UPDATE_USER_VIEW = "updateUser";

	static final String USER_UPDATE_URL = "/userUpdate";

	static final String USER_LOGIN_VIEW = "userLogin";

	static final String USER_LOGIN_URL = "/userLogin";

	static final String REGISTER_USER_VIEW = "registerUser";

	static final String REGISTER_USER_URL = "/registerUser";

	static final String INDEX_VIEW = "index";

	static final String GET_HOME_URL = "/getHome";

	private UserService userService;
	private Log log = LogFactory.getLog(MainController.class);

	@Autowired
	public MainController(UserService userService) {
		super();
		this.userService = userService;
	}

	/***
	 * 
	 * @return user to home page
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getHomePage() {
		return INDEX_VIEW; // what we actually sending back "/WEB-INF/views/index.jsp"
	}

	/***
	 * 
	 * @return user to home page
	 */
	@GetMapping(GET_HOME_URL)
	public String getHome() {
		return INDEX_VIEW;
	}

	/***
	 * register new users
	 * 
	 * @param session
	 * @param model
	 * @return to register.jsp page
	 */

	@GetMapping(REGISTER_USER_URL) // sends new users to registration page
	public String getRegisterPage(HttpSession session, Model model) {
		if (session.getAttribute(ACTIVE_USER_ATTR) != null) {
			return INDEX_VIEW;
		} else {
			User userToRegister = new User();
			model.addAttribute(BIND_USER_FORM_ATTR, userToRegister);
			return REGISTER_USER_VIEW;
		}
	}

	/**
	 * check if user is logged in, if not send to login page shortcut = Alt + shift
	 * + J
	 * 
	 * @param session
	 * @param model
	 * @return login page
	 */
	@GetMapping(USER_LOGIN_URL)
	public String getLoginPage(HttpSession session, Model model) {
		if (session.getAttribute(ACTIVE_USER_ATTR) != null) {
			return INDEX_VIEW;
		} else {
			model.addAttribute(USER_ATTR, new User());
			return USER_LOGIN_VIEW;
		}
	}

	/**
	 * check if user is loggin in, send to update page
	 * 
	 * @param model
	 * @return update user page
	 */
	@GetMapping(USER_UPDATE_URL)
	public String getUserUpdate(Model model) {
		User updatedUser = new User();
		model.addAttribute(BIND_NEW_UPDATE_USER_ATTR, updatedUser);
		return UPDATE_USER_VIEW;
	}

	/**
	 * send used to delete user page
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(DELETE_USER_URL)
	public String getRemoveUser(Model model) {
		User userToRemove = new User();
		model.addAttribute(BIND_TO_REMOVE_ATTR, userToRemove);
		return DELETE_USER_VIEW;
	}

	/**
	 * invalidate session for activeUser and return to registration/login page
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping(GET_LOGOUT_URL)
	public String Logout(HttpSession session) {
		session.invalidate();
		return INDEX_VIEW;
	}

	/**
	 * send not logged in users to check on their profile details
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping(GET_PROFILE_URL) // allows user to check their profile
	public String getUserProfile(HttpSession session, Model model) {
		if (session.getAttribute(ACTIVE_USER_ATTR) != null) {
			return USER_PROFILE_VIEW;
		} else {
			model.addAttribute(ERROR_MESSAGE_ATTR, LOGIN_TO_VIEW_PROFILE_MSG);
			return USER_LOGIN_VIEW;
		}
	}

	/**
	 * process registration of new users, check username existed
	 * @param session
	 * @param model
	 * @param userToRegister
	 * @return home page once successfully registered
	 */
	@PostMapping(PROCESS_USER_URL) 
	public String processRegisterPage(HttpSession session, Model model, User userToRegister) {
		User registeredUser = userService.save(userToRegister);
		if (registeredUser != null) {
			session.setAttribute(ACTIVE_USER_ATTR, userToRegister);
			model.addAttribute(MESSAGE_ATTR, REG_SUCCESS_MSG);
			return INDEX_VIEW;
		} else {
			model.addAttribute(BIND_USER_FORM_ATTR, userToRegister);
			model.addAttribute(ERROR_MESSAGE_ATTR,
					userToRegister.getUsername() + " is already taken. Please choose another username");
			return REGISTER_USER_VIEW;
		}

	}
	/**
	 * check if username password is registered
	 * @param session
	 * @param model
	 * @param user
	 * @return return home page once loggin in
	 */
	@PostMapping(PROCESS_LOGIN_URL) // check if username password is registered, mainpage or redirect login
	public String processUserLogin(HttpSession session, Model model, User user) {
		User loggedInUser = userService.login(user);
		if (loggedInUser != null) {
			session.setAttribute(ACTIVE_USER_ATTR, loggedInUser);
			model.addAttribute(MESSAGE_ATTR, LOGIN_SUCCESSFUL_MESSAGE);
			return INDEX_VIEW;
		} else {
			model.addAttribute(ERROR_MESSAGE_ATTR, WRONG_USER_PASS_MSG);
			return USER_LOGIN_VIEW;
		}
	}

	/**
	 * updated user new username and password and save to DB
	 * @param updatedUser
	 * @param session
	 * @param model 
	 * @return home page tell user info updated
	 */
	@PostMapping(UPDATE_USER_INFO_URL)
	public String processUserInfoUpdate(User updatedUser, HttpSession session, Model model) {
		log.info(updatedUser.getUsername());
		log.info(updatedUser.getPassword());
		User sessionUser = (User) session.getAttribute(ACTIVE_USER_ATTR);
		String newUsername = updatedUser.getUsername();
		String newPassword = updatedUser.getPassword();
		sessionUser.setUsername(newUsername);
		sessionUser.setPassword(newPassword);
		User newSessionUser = userService.save(sessionUser);
		session.setAttribute(ACTIVE_USER_ATTR, newSessionUser);
		model.addAttribute(MESSAGE_ATTR, UPDATED_USER_PASS_MSG);
		return INDEX_VIEW;
	}
	
	/**
	 * process deletion of user account, username and password must be same as registered
	 * @param userToRemove
	 * @param session
	 * @param model
	 * @return to home page once deletion successfull
	 */
	@PostMapping(PROCESS_DELETE_USER_VIEW)
	public String processToDeleteUser(User userToRemove, HttpSession session, Model model) {
		User sessionUser = (User) session.getAttribute(ACTIVE_USER_ATTR);
		if (userToRemove.getUsername().equals(sessionUser.getUsername())
				&& userToRemove.getPassword().equals(sessionUser.getPassword())) {
			userService.remove(userToRemove);
			session.invalidate();
			model.addAttribute(MESSAGE_ATTR, USER_DELETED_MSG);
			return INDEX_VIEW;
		} else {
			model.addAttribute(BIND_TO_REMOVE_ATTR, userToRemove);
			model.addAttribute(ERROR_MESSAGE_ATTR, WRONG_USER_PASS_MSG);
			return DELETE_USER_VIEW;
		}
	}
}
