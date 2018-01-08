package org.java.cart.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.java.cart.constants.Messages;
import org.java.cart.constants.OrderState;
import org.java.cart.domain.Order;
import org.java.cart.domain.Product;
import org.java.cart.domain.User;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.OrderNotFoundException;
import org.java.cart.exception.ProductNotFoundException;
import org.java.cart.service.OrderService;
import org.java.cart.utils.Utils;

public class OrderServiceImpl implements OrderService {

	private volatile static OrderServiceImpl _instance;
	private static Set<Order> orders;

	private OrderServiceImpl() {
	}

	public static OrderServiceImpl getInstance() {
		if (_instance == null) {
			synchronized (OrderServiceImpl.class) {
				if (_instance == null) {
					_instance = new OrderServiceImpl();
					init();
				}
			}
		}
		return _instance;
	}

	/**
	 * Method to initialize orders
	 */
	private static void init() {
		OrderServiceImpl.orders = new LinkedHashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.OrderService#createOrder(org.java.cart.domain.User)
	 */
	@Override
	public Order createOrder(User user) {
		if (Utils.isNull(user)) {
			throw new InvalidInputException(Messages.INVALID_USER_ERROR_MSG);
		}

		Order order = new Order();
		order.setOrderId(Utils.generateUUID());
		order.setOrderState(OrderState.NEW);
		order.setUser(user);
		order.setCreatedOn(LocalDateTime.now());
		orders.add(order);
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.OrderService#getOrder(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public Order getOrder(Long userId, String orderId) {
		if (Utils.isNull(orderId) || Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		Order order = findOrderByUserIdAndOrderId(userId, orderId);
		if (Utils.isNull(order)) {
			throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND_ERROR_MSG);
		}
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.OrderService#addProductToOrder(java.lang.Long,
	 * java.lang.String, org.java.cart.domain.Product)
	 */
	@Override
	public boolean addProductToOrder(Long userId, String orderId, Product product) {
		if (Utils.isNull(orderId) || Utils.isNull(userId) || Utils.isNull(product)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		Order order = findOrderByUserIdAndOrderId(userId, orderId);
		if (Utils.isNull(order)) {
			throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND_ERROR_MSG);
		}

		Set<Product> products = order.getProducts();
		if (Utils.isNull(products)) {
			products = new LinkedHashSet<>();
			products.add(product);
			order.setProducts(products);
		} else {
			if (checkIfProductAlreadyExist(products, product)) {
				product.setQuantity(product.getQuantity() + 1);
			}
			products.add(product);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.OrderService#getAllProductsFromOrder(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	public Set<Product> getAllProductsFromOrder(Long userId, String orderId) {
		if (Utils.isNull(orderId) || Utils.isNull(userId)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		Order order = findOrderByUserIdAndOrderId(userId, orderId);
		if (Utils.isNull(order)) {
			throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND_ERROR_MSG);
		}
		return order.getProducts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.OrderService#getProductFromOrder(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Product getProductFromOrder(Long userId, String orderId, String code) {
		if (Utils.isNull(orderId) || Utils.isNull(userId) || Utils.isNull(code)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		Order order = findOrderByUserIdAndOrderId(userId, orderId);
		if (Utils.isNull(order)) {
			throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND_ERROR_MSG);
		}

		Set<Product> products = order.getProducts();

		Product product = products.stream().filter(p -> p.getProduct().getCode().equals(code)).findAny().orElse(null);
		if (Utils.isNull(product)) {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND_ERROR_MSG);
		}
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.OrderService#deleteProductFromOrder(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean deleteProductFromOrder(Long userId, String orderId, String code) {
		if (Utils.isNull(orderId) || Utils.isNull(userId) || Utils.isNull(code)) {
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		}

		Order order = findOrderByUserIdAndOrderId(userId, orderId);
		if (Utils.isNull(order)) {
			throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND_ERROR_MSG);
		}

		Set<Product> products = order.getProducts();
		Product findProduct = products.stream().filter(p -> p.getProduct().getCode().equals(code)).findAny()
				.orElse(null);

		if (Utils.isNull(findProduct)) {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND_ERROR_MSG);
		}

		int quantity = findProduct.getQuantity();
		if (quantity > 1) {
			findProduct.setQuantity(quantity - 1);
			return true;
		}
		Product product = Utils.findAndRemove(products, p -> p.getProduct().getCode().equals(code));
		if (Utils.isNull(product))
			return false;

		return true;
	}

	/**
	 * Method to get order from the order based on the user and order ID
	 * 
	 * @param userId
	 * @param orderId
	 * @return
	 */
	private Order findOrderByUserIdAndOrderId(Long userId, String orderId) {
		return orders.stream().filter(p -> p.getUser().getUserId().equals(userId) && p.getOrderId().equals(orderId))
				.findAny().orElse(null);
	}

	/**
	 * Check if product already exist in the order of the user
	 * 
	 * @param products
	 * @param product
	 * @return
	 */
	private boolean checkIfProductAlreadyExist(Set<Product> products, Product product) {
		return products.contains(product);
	}

}
