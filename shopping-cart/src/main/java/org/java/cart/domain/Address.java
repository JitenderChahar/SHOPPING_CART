package org.java.cart.domain;

import java.io.Serializable;

import org.java.cart.utils.Utils;

public class Address implements Serializable {
	private static final long serialVersionUID = 2739562900547557186L;

	private String street;
	private String city;
	private String state;
	private String country;
	private String zipcode;

	public Address() {
	}

	public Address(String street, String city, String state, String country, String zipcode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
