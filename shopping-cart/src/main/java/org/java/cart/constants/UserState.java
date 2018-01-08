package org.java.cart.constants;

public enum UserState {
	NEW("new"), ACTIVE("active"), BLOCKED("blocked"), BANNED("banned");
	private final String state;

	UserState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
