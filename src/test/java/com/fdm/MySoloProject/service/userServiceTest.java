package com.fdm.MySoloProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.MySoloProject.dal.UserRepository;
import com.fdm.MySoloProject.model.User;

public class userServiceTest {

	UserService userService;

	@Mock
	UserRepository mockUserRepo;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(mockUserRepo);
	}

	@Test
	public void when_exit_identicalusername_inDb_return_null() {
		// Arrange
		String username = "Wallies";
		User toBeProcessed = new User(username, "asd", "asd", "asd", "asd", null, null);
		when(mockUserRepo.countByUsername(username)).thenReturn(22125412);
		// Act
		User returnedTestUser = userService.save(toBeProcessed);

		// Assert
		verify(mockUserRepo, times(1)).countByUsername(username);
		verify(mockUserRepo, never()).save(toBeProcessed);
		assertEquals(returnedTestUser, null);
	}

	@Test
	public void when_noidentical_username_return_registeredUser() {
		// Arrange
		User toBeProcessed = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User expectedUser = new User("asd", "asd", "asd", "asd", "asd", null, null);
		expectedUser.setUserId(1); // to simulate managed user
		when(mockUserRepo.countByUsername(toBeProcessed.getUsername())).thenReturn(0);
		when(mockUserRepo.save(toBeProcessed)).thenReturn(expectedUser);

		// Act
		User returnedTestUser = userService.save(toBeProcessed);
		// Assert
		verify(mockUserRepo, times(1)).countByUsername(toBeProcessed.getUsername());
		verify(mockUserRepo, times(1)).save(toBeProcessed);
		assertEquals(expectedUser, returnedTestUser);
	}

	@Test
	public void check_Loginfunction_return_founduser_Get() {
		// Arrange
		User toBeLoggedIn = new User("asd", "asd", "asd", "asd", "asd", null, null);
		Optional<User> testUser = Optional.of(new User("asd", "asd", "asd", "asd", "asd", null, null));
		when(mockUserRepo.findByUsernameAndPassword(toBeLoggedIn.getUsername(), toBeLoggedIn.getPassword()))
				.thenReturn(testUser);
		// Act
		User loggedInUser = userService.login(toBeLoggedIn);
		// Assert
		verify(mockUserRepo, times(1)).findByUsernameAndPassword(toBeLoggedIn.getUsername(),
				toBeLoggedIn.getPassword());
		assertEquals(loggedInUser, testUser.get());
		assertNotNull(loggedInUser);
	}

	@Test
	public void check_loginFunction_return_founduser_null() {
		// Arrange
		Optional<User> forTesting = Optional.of(new User("asdf", "asdf", "asd", "asd", "asd", null, null));
		Optional<User> testUser = Optional.of(new User("asd", "asd", "asd", "asd", "asd", null, null));
		when(mockUserRepo.findByUsernameAndPassword(forTesting.get().getUsername(), forTesting.get().getPassword()))
				.thenReturn(forTesting);
		// Act
		User tryToLoggin = userService.login(testUser.get());
		// Assert
		verify(mockUserRepo, times(1)).findByUsernameAndPassword(testUser.get().getUsername(),
				testUser.get().getPassword());
		assertEquals(tryToLoggin, null);
		assertNull(tryToLoggin);
	}

	@Test
	public void check_delete_user_function() {
		// Arrange
		User toBeDeleted = new User("asd", "asd", "asd", "asd", "asd", null, null);
		userService.save(toBeDeleted);
		// Act
		userService.remove(toBeDeleted);
		int result = mockUserRepo.countByUsername(toBeDeleted.getUsername());
		// Assert
		verify(mockUserRepo, times(1)).delete(toBeDeleted);
		assertEquals(result, 0);
	}

	@Test
	public void whenFindAllUser_return_list_of_users() {
		// Arrange
		List<User> testUsers = new ArrayList<>();
		User testUser1 = new User("asd", "asd", "asd", "asd", "asd", null, null);
		User testUser2 = new User("asdf", "asdf", "asdf", "asdf", "asdf", null, null);
		testUsers.add(testUser1);
		testUsers.add(testUser2);
		when(mockUserRepo.findAll()).thenReturn(testUsers);
		// Act
		List<User> result = userService.findAllUsers();
		// Assert
		verify(mockUserRepo, times(1)).findAll();
		assertEquals(result, testUsers);
	}

	@Test
	public void check_findbyid_return_founduser() {
		// Arrange
		Optional<User> foundUser = Optional.of(new User("asd", "asd", "asd", "asd", "asd", null, null));
		Optional<User> testUser = Optional.of(new User("asd", "asd", "asd", "asd", "asd", null, null));
		testUser.get().setUserId(1);
		when(mockUserRepo.findById(testUser.get().getUserId())).thenReturn(foundUser);
		// Act
		User result = userService.findById(testUser.get().getUserId());
		// Assert
		verify(mockUserRepo, times(1)).findById(testUser.get().getUserId());
		assertEquals(result, foundUser.get());
		assertNotNull(result);
	}

	@Test
	public void check_findbyid_return_null() {
		// Arrange
		Optional<User> foundUser = Optional.empty();
		Optional<User> testUser = Optional.of(new User("asd", "asd", "asd", "asd", "asd", null, null));
		testUser.get().setUserId(1);
		when(mockUserRepo.findById(testUser.get().getUserId())).thenReturn(foundUser);
		// Act
		User result = userService.findById(testUser.get().getUserId());
		// Assert
		verify(mockUserRepo, times(1)).findById(testUser.get().getUserId());
		assertEquals(result, null);
		assertNull(result);

	}
}
