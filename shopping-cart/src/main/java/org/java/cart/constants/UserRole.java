package org.java.cart.constants;

public enum UserRole {
	ADMIN("admin"), USER("user");
	private final String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getUserRole() {
		return role;
	}
}
