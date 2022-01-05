package com.fdm.MySoloProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import com.fdm.MySoloProject.model.User;
import com.fdm.MySoloProject.service.UserService;

public class MainControllerTest {
	private MainController mainController;

	@Mock
	private UserService mockUserService;

	@Mock
	private HttpSession mockSession;

	@Mock
	private Model mockModel;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		mainController = new MainController(mockUserService);
	}

	@Test
	public void getRegisterPageSpringForm_return_URL_registerUser() {
		// Arrange

		// Act
		String result = mainController.getRegisterPage(mockSession, mockModel);
		// Assert
		verify(mockModel, times(1)).addAttribute(eq(MainController.BIND_USER_FORM_ATTR), isA(User.class));
		assertEquals(MainController.REGISTER_USER_VIEW, result);
	}

	@Test
	public void getRegisterPageSpringForm_return_indexURL_when_noactiveUser() {
		// Arrange
		User activeUser = new User();
		mockSession.setAttribute("activeUser", activeUser);
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(activeUser);
		// Act
		String result = mainController.getRegisterPage(mockSession, mockModel);
		// Assert
		verify(mockSession, times(1)).getAttribute(eq(MainController.ACTIVE_USER_ATTR));
		assertEquals(MainController.INDEX_VIEW, result);
	}

	@Test
	public void LoginPage_return_USERloginview() {
		// Arrange

		// Act
		String result = mainController.getLoginPage(mockSession, mockModel);

		// Assert
		verify(mockModel, times(1)).addAttribute(eq(MainController.USER_ATTR), isA(User.class));
		assertEquals(MainController.USER_LOGIN_VIEW, result);
	}

	@Test
	public void LoginPage_return_IndexView_whenActiveUserPresent() {
		// Arrange
		User activeUser = new User();
		mockSession.setAttribute("activeUser", activeUser);
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(activeUser);
		// Act
		String result = mainController.getLoginPage(mockSession, mockModel);
		// Assert
		verify(mockSession, times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		assertEquals(MainController.INDEX_VIEW, result);
	}

	@Test
	public void getUserUpdate_return_url_return_view() {
		// Arrange

		// Act
		String result = mainController.getUserUpdate(mockModel);

		// Assert
		verify(mockModel, times(1)).addAttribute(eq(MainController.BIND_NEW_UPDATE_USER_ATTR), isA(User.class));
		assertEquals(MainController.UPDATE_USER_VIEW, result);
	}

	@Test
	public void getRemoveUser_return_url_return_view() {
		// Arrange

		// Act
		String result = mainController.getRemoveUser(mockModel);

		// Assert

		verify(mockModel, times(1)).addAttribute(eq(MainController.BIND_TO_REMOVE_ATTR), isA(User.class));
		assertEquals(MainController.DELETE_USER_VIEW, result);
	}

	@Test
	public void getLogOut_return_session_invalidate_return_indexPage() {
		// Arrange

		// Act
		String result = mainController.Logout(mockSession);
		// Assert
		assertEquals(MainController.INDEX_VIEW, result);
	}

	@Test
	public void getUserProfile_return_url_return_userprofileview() {
		// Arrange
		String test = "true";
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(test);
		// Act
		String result = mainController.getUserProfile(mockSession, mockModel);
		// Assert
		verify(mockSession, times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		assertEquals(MainController.USER_PROFILE_VIEW, result);
	}

	@Test
	public void getUserProfile_return_errorMessage_return_userLoginView() {
		// Arrange

		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(null);
		// Act
		String result = mainController.getUserProfile(mockSession, mockModel);
		// Assert
		verify(mockSession, times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		assertEquals(MainController.USER_LOGIN_VIEW, result);
	}

	@Test
	public void processRegisterPage_return_registeredUser() {
		// Arrange
		User testUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User savedTestUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		savedTestUserSF.setUserId(1);
		when(mockUserService.save(testUserSF)).thenReturn(savedTestUserSF);
		// Act
		String result = mainController.processRegisterPage(mockSession, mockModel, testUserSF);

		// Assert
		verify(mockUserService, atLeastOnce()).save(testUserSF);
		assertEquals(MainController.INDEX_VIEW, result);
	}

	@Test
	public void processRegisterPage_return_null_when_usernameUsed() {
		// Arrange
		User testUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User savedTestUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		when(mockUserService.save(testUserSF)).thenReturn(null);
		// Act
		mockUserService.save(savedTestUserSF);
		String result = mainController.processRegisterPage(mockSession, mockModel, testUserSF);

		// Assert
		verify(mockUserService, atLeastOnce()).save(testUserSF);
		assertEquals(MainController.REGISTER_USER_VIEW, result);
	}

	@Test
	public void processUserLogin_return_LoginPage() {
		// Arrange
		User testUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User loginedTestUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		loginedTestUser.setUserId(1);
		when(mockUserService.login(testUserSF)).thenReturn(loginedTestUser);
		// Act
		mockUserService.login(testUserSF);
		String result = mainController.processUserLogin(mockSession, mockModel, testUserSF);

		// Assert
		verify(mockUserService, atLeastOnce()).login(testUserSF);
		verify(mockSession, atLeastOnce()).setAttribute(MainController.ACTIVE_USER_ATTR, loginedTestUser);
		verify(mockModel,atLeastOnce()).addAttribute(MainController.MESSAGE_ATTR, MainController.LOGIN_SUCCESSFUL_MESSAGE);
		assertEquals(MainController.INDEX_VIEW, result);
	}
	
	@Test
	public void processUserLogin_return_UserloginView() {
		// Arrange
		User testUserSF = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User loginedTestUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		loginedTestUser.setUserId(1);
		when(mockUserService.login(testUserSF)).thenReturn(null);
		// Act
		String result = mainController.processUserLogin(mockSession, mockModel, testUserSF);

		// Assert
		verify(mockUserService, times(1)).login(testUserSF);
		verify(mockModel,times(1)).addAttribute(MainController.ERROR_MESSAGE_ATTR, MainController.WRONG_USER_PASS_MSG);
		assertEquals(MainController.USER_LOGIN_VIEW, result);
	}
	
	@Test
	public void processUserInfoUpdate_return_updatedUser_InDEX_VIEW() {
		// Arrange
		User oldUserInfo = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User updatedUserInfo = new User("asdf", "asd", "asd", "asdf", "asd", null, null);
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(oldUserInfo);
		when(mockUserService.save(updatedUserInfo)).thenReturn(updatedUserInfo);
		// Act
		String result = mainController.processUserInfoUpdate(updatedUserInfo, mockSession, mockModel);
		User newUpdatedUser = mockUserService.save(updatedUserInfo);
		
		// Assert
		verify(mockSession,times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		verify(mockModel,times(1)).addAttribute(MainController.MESSAGE_ATTR, MainController.UPDATED_USER_PASS_MSG);
		assertEquals(MainController.INDEX_VIEW, result);
	}
	
	@Test
	public void processToDeleteUser_return_removeUSer_indexView() {
		// Arrange
		User toBeDeleted = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User testUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(testUser);

		// Act
		String result = mainController.processToDeleteUser(toBeDeleted, mockSession, mockModel);
		
		// Assert
		verify(mockSession,times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		verify(mockModel,times(1)).addAttribute(MainController.MESSAGE_ATTR, MainController.USER_DELETED_MSG);
		assertEquals(MainController.INDEX_VIEW, result);
	}
	
	@Test
	public void processToDeleteUser_return_invalid_return_deleUserView() {
		// Arrange
		User toBeDeleted = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User testUser = new User("asdf", "asd", "asd", "asdf", "asd", null, null);
		when(mockSession.getAttribute(MainController.ACTIVE_USER_ATTR)).thenReturn(testUser);

		// Act
		String result = mainController.processToDeleteUser(toBeDeleted, mockSession, mockModel);
		
		// Assert
		verify(mockSession,times(1)).getAttribute(MainController.ACTIVE_USER_ATTR);
		verify(mockModel,times(1)).addAttribute(MainController.BIND_TO_REMOVE_ATTR, toBeDeleted);
		verify(mockModel,times(1)).addAttribute(MainController.ERROR_MESSAGE_ATTR, MainController.WRONG_USER_PASS_MSG);
		assertEquals(MainController.DELETE_USER_VIEW, result);
	}
}
