package org.java.cart.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.java.cart.constants.ProductCategory;
import org.java.cart.constants.UserRole;
import org.java.cart.constants.UserState;
import org.java.cart.domain.Address;
import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.ProductInfo;
import org.java.cart.domain.User;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.OrderNotFoundException;
import org.java.cart.exception.ProductNotFoundException;
import org.java.cart.service.impl.OrderServiceImpl;
import org.java.cart.utils.Utils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceTest {
	private static User user;
	private static Long userId;

	private static OrderService orderService;
	private static Order order;
	private static String orderId;

	private static Product product;

	@BeforeClass
	public static void init() {
		List<Address> addresses = new ArrayList<>();
		Address address = new Address("14/2 MAIN ST PO BOX 2827", "SEATTLE", "WA", "USA", "98104");
		addresses.add(address);
		user = new User(Utils.generateLongID(), "jastana@gmail.com", "J. Astana", UserRole.USER, addresses,
				UserState.ACTIVE, LocalDateTime.now());
		userId = user.getUserId();

		orderService = OrderServiceImpl.getInstance();

		ProductInfo productInfo = new ProductInfo(Utils.generateUUID(), "Dove Soap", 39.99,
				"Dove Soap is a beauty soap.", "Dove Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());
		product = new Product(productInfo, 1);
	}

	@Test(expected = InvalidInputException.class)
	public void test10CreateOrderWithUserAsNull() {
		orderService.createOrder(null);
	}

	@Test
	public void test11CreateOrderWithPositiveScenario() {
		Order createOrder = orderService.createOrder(user);
		assertNotNull(createOrder);
		order = createOrder;
		orderId = createOrder.getOrderId();
	}

	@Test(expected = InvalidInputException.class)
	public void test12GetOrderWithOrderIdAndUserIdAsNull() {
		orderService.getOrder(null, null);
	}

	@Test
	public void test13GetOrderWithPositiveScenario() {
		Order returnedOrder = orderService.getOrder(userId, orderId);
		assertTrue(order == returnedOrder);
	}

	@Test(expected = OrderNotFoundException.class)
	public void test14GetOrderWithNegativeScenario() {
		orderService.getOrder(userId, Utils.generateUUID());
	}

	@Test(expected = InvalidInputException.class)
	public void test15AddProductToOrderWithOrderIdUserIdAndProductAsNull() {
		orderService.addProductToOrder(null, null, null);
	}

	@Test(expected = OrderNotFoundException.class)
	public void test16AddProductToOrderWithNegativeScenario() {
		orderService.addProductToOrder(userId, Utils.generateUUID(), product);
	}

	@Test
	public void test17AddProductToOrderWithPositiveScenario() {
		assertTrue(orderService.addProductToOrder(userId, orderId, product));
	}

	@Test
	public void test18AddProductToOrderWithQuntityIncreased() {
		int expectedCount = 2;
		int actual = 0;
		orderService.addProductToOrder(userId, orderId, product);
		Order returnedOrder = orderService.getOrder(userId, orderId);
		Set<Product> products = returnedOrder.getProducts();
		Optional<Product> findAny = products.stream().filter(p -> p.getProduct().getName().equals("Dove Soap"))
				.findAny();
		if (findAny.isPresent()) {
			actual = findAny.get().getQuantity();
		}
		assertEquals(expectedCount, actual);
	}

	@Test(expected = InvalidInputException.class)
	public void test19GetAllProductsFromOrderWithUserIdAndOrderId() {
		orderService.getAllProductsFromOrder(null, null);
	}

	@Test(expected = OrderNotFoundException.class)
	public void test20GetAllProductsFromOrderWithNegativeScenario() {
		orderService.getAllProductsFromOrder(userId, Utils.generateUUID());
	}

	@Test
	public void test21GetAllProductsFromOrderWithPositiveScenario() {
		int expected = 1;
		Set<Product> allProductsFromOrder = orderService.getAllProductsFromOrder(userId, orderId);
		assertTrue(expected == allProductsFromOrder.size());
	}

	@Test(expected = InvalidInputException.class)
	public void test22GetProductFromOrderWithOrderIdCodeAndUserIdAsNull() {
		orderService.getProductFromOrder(null, null, null);
	}

	@Test(expected = ProductNotFoundException.class)
	public void test23GetProductFromOrderWithNegativeScenario() {
		orderService.getProductFromOrder(userId, orderId, Utils.generateUUID());
	}

	@Test
	public void test24GetProductFromOrderWithPositiveScenario() {
		Product productFromOrder = orderService.getProductFromOrder(userId, orderId, product.getProduct().getCode());
		assertTrue(product == productFromOrder);
	}

	@Test(expected = InvalidInputException.class)
	public void test25DeleteProductFromOrderWithOrderIdCodeAndUserIdAsNull() {
		orderService.deleteProductFromOrder(null, null, null);
	}

	@Test(expected = ProductNotFoundException.class)
	public void test26DeleteProductFromOrderWithNegativeScenario() {
		orderService.deleteProductFromOrder(userId, orderId, Utils.generateUUID());
	}

	@Test
	public void test27DeleteProductFromOrderWithPositiveScenario() {
		assertTrue(orderService.deleteProductFromOrder(userId, orderId, product.getProduct().getCode()));
	}

}
