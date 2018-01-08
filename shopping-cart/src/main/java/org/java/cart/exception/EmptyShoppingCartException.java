package org.java.cart.exception;

public class EmptyShoppingCartException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyShoppingCartException(String message) {
		super(message);
	}
}
