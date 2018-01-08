package org.java.cart.service;

import java.util.Set;

import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.User;

public interface OrderService {

	/**
	 * Method to create a order per user
	 * 
	 * @param user
	 * @return
	 */
	Order createOrder(User user);

	/**
	 * Method to get the order by order and user ID
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	Order getOrder(Long userId, String orderId);

	/**
	 * Method to add product to the order of the user
	 * <p>
	 * If same product is added multiple times then it increase its quantity by
	 * one rather than adding a new product
	 * 
	 * @param userId
	 * @param orderId
	 * @param product
	 * @return
	 */
	boolean addProductToOrder(Long userId, String orderId, Product product);

	/**
	 * Method to get all products in a user order
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	Set<Product> getAllProductsFromOrder(Long userId, String orderId);

	/**
	 * Method to get a Product from the order
	 * 
	 * @param userId
	 * @param orderId
	 * @param code
	 * @return
	 */
	Product getProductFromOrder(Long userId, String orderId, String code);

	/**
	 * Method to remove a Product from the order
	 * <p>
	 * If quantity of product is greater than 1 then It decrease the quantity of
	 * the order by one else it removes the product
	 * 
	 * @param userId
	 * @param orderId
	 * @param code
	 * @return
	 */
	boolean deleteProductFromOrder(Long userId, String orderId, String code);
}
