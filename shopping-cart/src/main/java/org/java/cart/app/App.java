package org.java.cart.app;

import java.util.Set;

import org.apache.log4j.Logger;
import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.ProductInfo;
import org.java.cart.domain.ShoppingCart;
import org.java.cart.domain.User;
import org.java.cart.service.OrderService;
import org.java.cart.service.ProductInfoService;
import org.java.cart.service.ShoppingCartService;
import org.java.cart.service.UserService;
import org.java.cart.service.impl.OrderServiceImpl;
import org.java.cart.service.impl.ProductInfoServiceImpl;
import org.java.cart.service.impl.ShoppingCartServiceImpl;
import org.java.cart.service.impl.UserServiceImpl;
import org.java.cart.utils.Utils;

public class App {
	final static Logger logger = Logger.getLogger(Utils.class);

	public static void main(String[] args) {

		logger.info("-------------------------------------------------------------------------------------------");
		logger.info("Starting the Shopping Cart Application");
		logger.info("-------------------------------------------------------------------------------------------");

		String userEmail = "john@gmail.com";
		String doveSoapProductName = "Dove Soap";
		String axeDeoProductName = "Axe Deo";

		logger.info("Creating UserService");
		UserService userService = UserServiceImpl.getInstance();

		Set<User> allUsers = userService.getAllUsers();
		if (allUsers == null) {
			logger.error("No users found in the system.");
		}
		logger.info("All users in system : " + allUsers);

		logger.info("Get the user by email Id " + userEmail);
		User user = allUsers.stream().filter(p -> p.getUserEmailId().equals(userEmail)).findAny().orElse(null);

		if (user == null) {
			logger.error("User not found with " + userEmail);
		}
		logger.info("User " + user);

		Long userId = user.getUserId();
		logger.info("User ID " + userId);

		logger.info("Creating ProductInfoService");
		ProductInfoService productInfoService = ProductInfoServiceImpl.getInstance();
		Set<ProductInfo> allProducts = productInfoService.getAllProducts();

		if (allProducts == null) {
			logger.error("No products found in the system.");
		}
		logger.info("All products in system : " + allProducts);

		logger.info("Get the product info by name as " + doveSoapProductName);
		ProductInfo productInfo = allProducts.stream().filter(p -> p.getName().equals(doveSoapProductName)).findAny()
				.orElse(null);

		if (productInfo == null) {
			logger.error("Product not found with name as " + doveSoapProductName);
		}
		logger.info("Product : " + productInfo);

		logger.info("Get the product info by name as " + axeDeoProductName);
		ProductInfo productInfo1 = allProducts.stream().filter(p -> p.getName().equals(axeDeoProductName)).findAny()
				.orElse(null);

		if (productInfo == null) {
			logger.error("Product not found with name as " + axeDeoProductName);
		}
		logger.info("Product : " + productInfo1);

		logger.info("Creating doveSoapProduct");
		Product doveSoapProduct = new Product(productInfo, 1);

		logger.info("Creating axeDeoProduct");
		Product axeDeoProduct = new Product(productInfo1, 1);

		logger.info("Creating OrderService");
		OrderService orderService = OrderServiceImpl.getInstance();

		logger.info("Creating an order for user");
		Order order = orderService.createOrder(user);

		String orderId = order.getOrderId();
		logger.info("Get order id as " + orderId);

		logger.info("Adding 2 dove soap products in the order");
		orderService.addProductToOrder(userId, orderId, doveSoapProduct);
		orderService.addProductToOrder(userId, orderId, doveSoapProduct);
		
		logger.info("Adding 2 axe deo products in the order");
		orderService.addProductToOrder(userId, orderId, axeDeoProduct);
		orderService.addProductToOrder(userId, orderId, axeDeoProduct);

		logger.info(orderService.getAllProductsFromOrder(userId, orderId));

		logger.info("Creating ShoppingCartService");
		ShoppingCartService shoppingCartService = ShoppingCartServiceImpl.getInstance();

		logger.info("Creating a shopping cart for user");
		ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(user);

		String cartId = shoppingCart.getCartId();
		logger.info("Get shopping cart id as " + cartId);

		logger.info("Adding order to the shopping cart");
		shoppingCartService.addOrderToCart(cartId, userId, order);

		logger.info("Shopping cart Summary : " + shoppingCartService.getTotalCartAmount(cartId, userId));

	}
}
