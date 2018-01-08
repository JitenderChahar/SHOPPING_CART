package org.java.cart.constants;

public enum ProductCategory {
	COSMATIC("cosmatic"), HOSUSE_HOLD("house_hold"), CLOTHING("clothing"), ELECTRICAL("electical");
	private final String category;

	ProductCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
