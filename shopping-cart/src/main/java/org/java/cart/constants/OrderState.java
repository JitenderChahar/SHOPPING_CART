package org.java.cart.constants;

public enum OrderState {
	NEW("new"), IN_CART("in_cart"), PROCESSED("processed");
	private final String state;

	OrderState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
