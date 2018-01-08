package org.java.cart.service;

import java.util.Set;

import org.java.cart.domain.User;

public interface UserService {

	/**
	 * Method to add new user in the system
	 * <p>
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param user
	 * @return
	 */
	boolean saveUser(User user);

	/**
	 * Method to get the user by user ID
	 * <p>
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * <li>USER</li>
	 * </ul>
	 * 
	 * @param userId
	 * @return
	 */
	User getUserByID(Long userId);

	/**
	 * Method to get all the Users
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @return
	 */
	Set<User> getAllUsers();

	/**
	 * Method to deleter a user from the system
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param userId
	 * @return
	 */
	boolean deleteUser(Long userId);

	/**
	 * Method to update the user account
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param user
	 * @return
	 */
	User updateUser(User user);
}
