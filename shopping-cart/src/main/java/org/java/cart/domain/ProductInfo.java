package org.java.cart.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.java.cart.constants.ProductCategory;
import org.java.cart.utils.Utils;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = -8635060438178596954L;

	private String code;
	private String name;
	private double price;
	private String description;
	private String manufacturer;
	private ProductCategory category;
	private LocalDateTime creadtedOn;

	public ProductInfo() {
	}

	public ProductInfo(String code, String name, double price, String description, String manufacturer,
			ProductCategory category, LocalDateTime creadtedOn) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.description = description;
		this.manufacturer = manufacturer;
		this.category = category;
		this.creadtedOn = creadtedOn;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public LocalDateTime getCreadtedOn() {
		return creadtedOn;
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ProductInfo other = (ProductInfo) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.convertObjectToJSONStr(this);
	}

}
