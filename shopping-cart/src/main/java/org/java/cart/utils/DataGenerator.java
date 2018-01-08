package org.java.cart.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.java.cart.constants.ProductCategory;
import org.java.cart.constants.UserRole;
import org.java.cart.constants.UserState;
import org.java.cart.domain.Address;
import org.java.cart.domain.ProductInfo;
import org.java.cart.domain.User;

public class DataGenerator {
	private DataGenerator() {
	}

	public static Set<User> getUsersData() {
		Set<User> users = new LinkedHashSet<>();

		List<Address> addresses = new ArrayList<>();
		Address address1 = new Address("100 MAIN ST PO BOX 1022", "SEATTLE", "WA", "USA", "98104");
		addresses.add(address1);
		User user1 = new User(Utils.generateLongID(), "john@gmail.com", "JOHN SMITH", UserRole.USER, addresses,
				UserState.ACTIVE, LocalDateTime.now());

		List<Address> addresses1 = new ArrayList<>();
		Address address2 = new Address("MEGASYSTEMS INC SUITE 5A-1204", "TUCSON", "AZ", "USA", "85705");
		addresses1.add(address2);
		User user2 = new User(Utils.generateLongID(), "mary@gmail.com", "MARY ROE", UserRole.USER, addresses1,
				UserState.ACTIVE, LocalDateTime.now());

		users.add(user1);
		users.add(user2);

		return users;
	}

	public static Set<ProductInfo> getProductsInfoData() {
		Set<ProductInfo> products = new LinkedHashSet<>();
		ProductInfo product1 = new ProductInfo(Utils.generateUUID(), "Dove Soap", 39.99, "Dove Soap is a beauty soap.",
				"Dove Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());
		ProductInfo product2 = new ProductInfo(Utils.generateUUID(), "Axe Deo", 99.99, "Axe Deo is a body Deodrant.",
				"Axe Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());
		ProductInfo product3 = new ProductInfo(Utils.generateUUID(), "Killer Deo", 150.56,
				"Killer Deo is a body Deodrant.", "Killer Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());
		products.add(product1);
		products.add(product2);
		products.add(product3);
		return products;
	}
}
