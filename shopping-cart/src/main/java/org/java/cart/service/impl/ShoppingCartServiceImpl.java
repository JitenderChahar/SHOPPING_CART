package org.java.cart.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.java.cart.constants.Messages;
import org.java.cart.constants.OrderState;
import org.java.cart.domain.CartSummary;
import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.ShoppingCart;
import org.java.cart.domain.User;
import org.java.cart.exception.EmptyShoppingCartException;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.ShoppingCartNotFoundException;
import org.java.cart.service.ShoppingCartService;
import org.java.cart.utils.Utils;

public class ShoppingCartServiceImpl implements ShoppingCartService {

	private volatile static ShoppingCartServiceImpl _instance;
	private static Set<ShoppingCart> carts;

	private ShoppingCartServiceImpl() {
	}

	public static ShoppingCartServiceImpl getInstance() {
		if (_instance == null) {
			synchronized (ShoppingCartServiceImpl.class) {
				if (_instance == null) {
					_instance = new ShoppingCartServiceImpl();
					init();
				}
			}
		}
		return _instance;
	}

	/**
	 * Method to initialize the shopping carts
	 */
	private static void init() {
		ShoppingCartServiceImpl.carts = new LinkedHashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ShoppingCartService#createShoppingCart(org.java.
	 * cart.domain.User)
	 */
	@Override
	public ShoppingCart createShoppingCart(User user) {
		if (Utils.isNull(user)) {
			throw new InvalidInputException(Messages.INVALID_USER_ERROR_MSG);
		}

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setCartId(Utils.generateUUID());
		shoppingCart.setUser(user);
		carts.add(shoppingCart);
		return shoppingCart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.ShoppingCartService#getShoppingCart(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	public ShoppingCart getShoppingCart(String cartId, Long userId) {
		if (Utils.isNull(cartId) || Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		ShoppingCart shoppingCart = findShoppingCartByUserIdAndCartId(userId, cartId);
		checkAndThrowCartNotFoundException(shoppingCart);
		return shoppingCart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.ShoppingCartService#addOrderToCart(java.lang.
	 * String, java.lang.Long, org.java.cart.domain.Order)
	 */
	@Override
	public boolean addOrderToCart(String cartId, Long userId, Order order) {
		if (Utils.isNull(cartId) || Utils.isNull(userId) || Utils.isNull(order)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		ShoppingCart shoppingCart = findShoppingCartByUserIdAndCartId(userId, cartId);
		checkAndThrowCartNotFoundException(shoppingCart);
		order.setOrderState(OrderState.IN_CART);
		shoppingCart.setOrder(order);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ShoppingCartService#removeOrderFromCart(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	public boolean removeOrderFromCart(String cartId, Long userId) {
		if (Utils.isNull(cartId) || Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		ShoppingCart shoppingCart = findShoppingCartByUserIdAndCartId(userId, cartId);
		checkAndThrowCartNotFoundException(shoppingCart);

		Order order = shoppingCart.getOrder();
		if (Utils.isNotNull(order)) {
			shoppingCart.setOrder(null);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ShoppingCartService#getTotalCartAmount(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	public CartSummary getTotalCartAmount(String cartId, Long userId) {
		if (Utils.isNull(cartId) || Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		CartSummary cartSummary = new CartSummary();

		ShoppingCart shoppingCart = findShoppingCartByUserIdAndCartId(userId, cartId);
		checkAndThrowCartNotFoundException(shoppingCart);

		Order order = shoppingCart.getOrder();
		if (Utils.isNull(order)) {
			throw new EmptyShoppingCartException(Messages.EMPTY_SHOPPING_CART_ERROR_MSG);
		}

		double productsTotalAmount = 0;
		double productTotalTaxAmount = 0;
		Set<Product> products = order.getProducts();
		for (Product product : products) {
			double productAmount = product.getProduct().getPrice() * product.getQuantity();
			double productTax = Utils.calculateTax(productAmount, product.getTax());
			productsTotalAmount += productAmount;
			productTotalTaxAmount += productTax;
		}

		cartSummary.setTotalCartAmount(Utils.round(productsTotalAmount + productTotalTaxAmount, 2));
		cartSummary.setTotalTax(Utils.round(productTotalTaxAmount, 2));
		return cartSummary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ShoppingCartService#checkoutAndPayment(java.lang.
	 * String, java.lang.Long)
	 */
	@Override
	public String checkoutAndPayment(String cartId, Long userId) {
		ShoppingCart shoppingCart = findShoppingCartByUserIdAndCartId(userId, cartId);
		checkAndThrowCartNotFoundException(shoppingCart);

		Order order = shoppingCart.getOrder();
		order.setOrderState(OrderState.PROCESSED);
		boolean removeOrderFromCart = removeOrderFromCart(cartId, userId);
		if (removeOrderFromCart) {
			return Messages.ORDER_PROCESSED_SUCCESS_MSG;
		} else {
			return Messages.ORDER_PROCESSED_FAILURE_ERROR_MSG;
		}
	}

	/**
	 * Method to find the shopping cart for the user
	 * 
	 * @param userId
	 * @param cartId
	 * @return
	 */
	private ShoppingCart findShoppingCartByUserIdAndCartId(Long userId, String cartId) {
		return carts.stream().filter(p -> p.getUser().getUserId().equals(userId) && p.getCartId().equals(cartId))
				.findAny().orElse(null);
	}

	/**
	 * Method to check and throw cart not found exception
	 * 
	 * @param shoppingCart
	 */
	private void checkAndThrowCartNotFoundException(ShoppingCart shoppingCart) {
		if (Utils.isNull(shoppingCart)) {
			throw new ShoppingCartNotFoundException(Messages.SHOPPING_CART_FOR_USER_NOT_FOUND_ERROR_MSG);
		}
	}

}
