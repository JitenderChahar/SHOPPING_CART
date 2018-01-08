package org.java.cart.domain;

import java.io.Serializable;

import org.java.cart.constants.Constants;
import org.java.cart.constants.Messages;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.utils.Utils;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private ProductInfo product;
	private int quantity;
	private double tax;

	public Product() {
	}

	public Product(ProductInfo product, int quantity) {
		if (quantity < 1)
			throw new InvalidInputException(Messages.INVALID_ARGUMENTS_ERROR_MSG);
		this.product = product;
		this.quantity = quantity;
		this.tax = Constants.TAX_ON_PRODUCTS;
	}

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Utils.convertObjectToJSONStr(this);
	}

}
