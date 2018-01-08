package org.java.cart.domain;

import java.io.Serializable;

import org.java.cart.utils.Utils;

public class CartSummary implements Serializable {
	private static final long serialVersionUID = -1738641871029195465L;

	private double totalTax;
	private double totalCartAmount;

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalCartAmount() {
		return totalCartAmount;
	}

	public void setTotalCartAmount(double totalCartAmount) {
		this.totalCartAmount = totalCartAmount;
	}

	@Override
	public String toString() {
		return Utils.convertObjectToJSONStr(this);
	}

}
