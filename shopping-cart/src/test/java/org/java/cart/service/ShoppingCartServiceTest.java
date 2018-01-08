package org.java.cart.service;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.java.cart.constants.ProductCategory;
import org.java.cart.constants.UserRole;
import org.java.cart.constants.UserState;
import org.java.cart.domain.Address;
import org.java.cart.domain.CartSummary;
import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.ProductInfo;
import org.java.cart.domain.ShoppingCart;
import org.java.cart.domain.User;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.ShoppingCartNotFoundException;
import org.java.cart.service.impl.OrderServiceImpl;
import org.java.cart.service.impl.ShoppingCartServiceImpl;
import org.java.cart.utils.Utils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShoppingCartServiceTest {
	private static User user;
	private static Long userId;

	private static OrderService orderService;
	private static Order order;
	private static String orderId;

	private static ShoppingCartService shoppingCartService;
	private static ShoppingCart shoppingCart;
	private static String cartId;

	private static Product product;

	@BeforeClass
	public static void init() {
		List<Address> addresses = new ArrayList<>();
		Address address = new Address("14/2 MAIN ST PO BOX 2827", "SEATTLE", "WA", "USA", "98104");
		addresses.add(address);
		user = new User(Utils.generateLongID(), "sofia@gmail.com", "Sofia", UserRole.USER, addresses, UserState.ACTIVE,
				LocalDateTime.now());
		userId = user.getUserId();

		ProductInfo productInfo = new ProductInfo(Utils.generateUUID(), "Dove Soap", 39.99,
				"Dove Soap is a beauty soap.", "Dove Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());
		product = new Product(productInfo, 2);

		orderService = OrderServiceImpl.getInstance();
		order = orderService.createOrder(user);
		orderId = order.getOrderId();
		orderService.addProductToOrder(userId, orderId, product);

		shoppingCartService = ShoppingCartServiceImpl.getInstance();
	}

	@Test(expected = InvalidInputException.class)
	public void test10CreateShoppingCartWithUserAsNull() {
		shoppingCartService.createShoppingCart(null);
	}

	@Test
	public void test11CreateShoppingCart() {
		ShoppingCart createShoppingCart = shoppingCartService.createShoppingCart(user);
		assertNotNull(createShoppingCart);
		shoppingCart = createShoppingCart;
		cartId = shoppingCart.getCartId();
	}

	@Test(expected = InvalidInputException.class)
	public void test12GetShoppingCartWithInvalidArgument() {
		shoppingCartService.getShoppingCart(null, null);
	}

	@Test(expected = ShoppingCartNotFoundException.class)
	public void test13GetShoppingCartWithNagativeScenario() {
		shoppingCartService.getShoppingCart(cartId, Utils.generateLongID());
	}

	@Test
	public void test14GetShoppingCartWithPositiveScenario() {
		ShoppingCart returnedShoppingCart = shoppingCartService.getShoppingCart(cartId, userId);
		assertTrue(shoppingCart == returnedShoppingCart);
	}

	@Test(expected = InvalidInputException.class)
	public void test15AddOrderToCartWithInvalidArgumentsAsNull() {
		shoppingCartService.addOrderToCart(null, null, null);
	}

	@Test
	public void test16AddOrderToCartWithPositiveScenario() {
		assertTrue(shoppingCartService.addOrderToCart(cartId, userId, order));
	}

	@Test(expected = InvalidInputException.class)
	public void test17GetTotalCartAmountWithInvalidArgumentsAsNull() {
		shoppingCartService.getTotalCartAmount(null, null);
	}

	@Test
	public void test18GetTotalCartAmount() {
		double expectedTax = 10;
		double expectedCartAmount = 89.98;
		CartSummary totalCartAmount = shoppingCartService.getTotalCartAmount(cartId, userId);
		assertTrue(expectedTax == totalCartAmount.getTotalTax());
		assertTrue(expectedCartAmount == totalCartAmount.getTotalCartAmount());
	}

	@Test(expected = InvalidInputException.class)
	public void testRemoveOrderFromCartWithInvalidArgumentsAsNull() {
		shoppingCartService.removeOrderFromCart(null, null);
	}

	@Test
	public void testRemoveOrderFromCartWith() {
		assertTrue(shoppingCartService.removeOrderFromCart(cartId, userId));
	}

}
