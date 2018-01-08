package org.java.cart.service;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.java.cart.constants.UserRole;
import org.java.cart.constants.UserState;
import org.java.cart.domain.Address;
import org.java.cart.domain.User;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.UserNotFoundException;
import org.java.cart.service.impl.UserServiceImpl;
import org.java.cart.utils.Utils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

	private static UserService userService;
	private static User user;
	private static Long userId;

	@BeforeClass
	public static void init() {
		userService = UserServiceImpl.getInstance();

		List<Address> addresses = new ArrayList<>();
		Address address = new Address("14/2 MAIN ST PO BOX 2827", "SEATTLE", "WA", "USA", "98104");
		addresses.add(address);
		user = new User(Utils.generateLongID(), "jastana@gmail.com", "J. Astana", UserRole.USER, addresses,
				UserState.ACTIVE, LocalDateTime.now());
		userId = user.getUserId();

	}

	@Test(expected = InvalidInputException.class)
	public void test10SaveUserWithUserAsNull() {
		userService.saveUser(null);
	}

	@Test
	public void test11SaveUserWithPositiveScenario() {
		assertTrue(userService.saveUser(user));

	}

	@Test
	public void test12SaveUserWithNegativeScenario() {
		assertFalse(userService.saveUser(user));
	}

	@Test
	public void test13GetUserByIDWithPositiveScenario() {
		User userByID = userService.getUserByID(userId);
		assertEquals("Ojects are not same", user, userByID);
	}

	@Test(expected = UserNotFoundException.class)
	public void test14GetUserByIDWithNegativeScenario() {
		userService.getUserByID(Utils.generateLongID());
	}

	@Test(expected = InvalidInputException.class)
	public void test15GetUserByIDWithUserIdAsNull() {
		userService.getUserByID(null);
	}

	@Test
	public void test16GetAllUsers() {
		int expected = 3;
		Set<User> allUsers = userService.getAllUsers();
		assertTrue(allUsers.size() == expected);
	}

	@Test(expected = InvalidInputException.class)
	public void test17UpdateUserWithUserAsNull() {
		userService.updateUser(null);
	}

	@Test
	public void test18UpdateUserWithPositiveScenario() {
		String expectedUserName = "J. Astana Clark";
		user.setUserName("J. Astana Clark");
		User updateUser = userService.updateUser(user);
		assertTrue(updateUser == user);
		assertEquals(expectedUserName, user.getUserName());

	}

	@Test(expected = InvalidInputException.class)
	public void test19DeleteUserWithUserIdAsNull() {
		userService.deleteUser(null);
	}

	@Test(expected = UserNotFoundException.class)
	public void test20DeleteUserWithNegativeScenario() {
		userService.deleteUser(Utils.generateLongID());
	}

	@Test
	public void test21DeleteUserWithPositiveScenario() {
		assertTrue(userService.deleteUser(userId));
	}

}
