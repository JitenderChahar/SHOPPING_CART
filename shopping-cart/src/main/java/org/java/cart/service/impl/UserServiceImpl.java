package org.java.cart.service.impl;

import java.util.Set;

import org.java.cart.constants.Messages;
import org.java.cart.constants.UserState;
import org.java.cart.domain.User;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.UserNotFoundException;
import org.java.cart.service.UserService;
import org.java.cart.utils.DataGenerator;
import org.java.cart.utils.Utils;

public class UserServiceImpl implements UserService {

	private volatile static UserServiceImpl _instance;
	// user list
	// This can be later replace by actual DAO layer
	private static Set<User> users;

	private UserServiceImpl() {
		// preventing Singleton object instantiation from outside
	}

	/*
	 * An implementation of double checked locking of Singleton.
	 */
	public static UserServiceImpl getInstance() {
		if (_instance == null) {
			synchronized (UserServiceImpl.class) {
				if (_instance == null) {
					_instance = new UserServiceImpl();
					populateUsers();
				}
			}
		}
		return _instance;
	}

	/**
	 * Method to populate users with pre defined users
	 */
	private static void populateUsers() {
		setUsers(DataGenerator.getUsersData());
	}

	// implement readResolve method to prevent the singleton in case in future
	// Serializable implemented
	protected Object readResolve() {
		return _instance;
	}

	/**
	 * Method to set the users data
	 * 
	 * @param users
	 */
	private static void setUsers(Set<User> users) {
		if (users.isEmpty()) {
			throw new InvalidInputException(Messages.INVALID_USERS_ERROR_MSG);
		}
		UserServiceImpl.users = users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.UserService#saveUser(org.java.cart.domain.User)
	 */
	@Override
	public boolean saveUser(User user) {
		if (Utils.isNull(user)) {
			throw new InvalidInputException(Messages.INVALID_USER_ERROR_MSG);
		}

		user.setUserState(UserState.ACTIVE);
		return users.add(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.UserService#getUserByID(java.lang.Long)
	 */
	@Override
	public User getUserByID(Long userId) {
		if (Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_USER_ID_ERROR_MSG);
		}

		User user = findUserById(userId);
		if (Utils.isNull(user)) {
			throw new UserNotFoundException(Messages.USER_NOT_FOUND_ERROR_MSG);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.UserService#getAllUsers()
	 */
	@Override
	public Set<User> getAllUsers() {
		if (users.isEmpty()) {
			throw new UserNotFoundException(Messages.USERS_NOT_FOUND_ERROR_MSG);
		}
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.UserService#deleteUser(java.lang.Long)
	 */
	@Override
	public boolean deleteUser(Long userId) {
		if (Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_USER_ID_ERROR_MSG);
		}

		User user = Utils.findAndRemove(users, p -> p.getUserId().equals(userId));
		if (Utils.isNull(user))
			throw new UserNotFoundException("For user id " + userId + " " + Messages.USER_NOT_FOUND_ERROR_MSG);
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.UserService#updateUser(org.java.cart.domain.User)
	 */
	@Override
	public User updateUser(User user) {
		if (Utils.isNull(user)) {
			throw new InvalidInputException(Messages.INVALID_USER_ERROR_MSG);
		}

		User findUserById = findUserById(user.getUserId());
		if (Utils.isNull(findUserById)) {
			throw new UserNotFoundException(Messages.USER_NOT_FOUND_ERROR_MSG);
		}

		findUserById.setUserName(user.getUserName());
		findUserById.setUserState(user.getUserState());
		findUserById.setShippingAddresses(user.getShippingAddresses());
		return findUserById;
	}

	/**
	 * method to find user by Id
	 * 
	 * @param userId
	 * @return
	 */
	private User findUserById(Long userId) {
		return users.stream().filter(p -> p.getUserId().equals(userId)).findAny().orElse(null);
	}

}
