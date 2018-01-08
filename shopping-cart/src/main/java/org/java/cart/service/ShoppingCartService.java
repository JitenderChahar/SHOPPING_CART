package org.java.cart.service;

import org.java.cart.domain.CartSummary;
import org.java.cart.domain.Order;
import org.java.cart.domain.ShoppingCart;
import org.java.cart.domain.User;

public interface ShoppingCartService {
	/**
	 * Create shopping cart as per user
	 * 
	 * @param user
	 * @return
	 */
	ShoppingCart createShoppingCart(User user);

	/**
	 * Method to get the shopping cart of the user
	 * 
	 * @param cartId
	 * @param userId
	 * @return
	 */
	ShoppingCart getShoppingCart(String cartId, Long userId);

	/**
	 * Method to add order to the cart
	 * 
	 * @param cartId
	 * @param userId
	 * @param order
	 * @return
	 */
	boolean addOrderToCart(String cartId, Long userId, Order order);

	/**
	 * Method to remove order from the user cart
	 * 
	 * @param cartId
	 * @param userId
	 * @return
	 */
	boolean removeOrderFromCart(String cartId, Long userId);

	/**
	 * Method to get the summary of the shopping cart
	 * 
	 * @param cartId
	 * @param userId
	 * @return
	 */
	CartSummary getTotalCartAmount(String cartId, Long userId);

	/**
	 * Method for checkout and payment process
	 * 
	 * @param cartId
	 * @param userId
	 * @return
	 */
	String checkoutAndPayment(String cartId, Long userId);
}
