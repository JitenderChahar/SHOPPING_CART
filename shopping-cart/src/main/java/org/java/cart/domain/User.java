package org.java.cart.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.java.cart.constants.UserRole;
import org.java.cart.constants.UserState;
import org.java.cart.utils.Utils;

public class User implements Serializable {
	private static final long serialVersionUID = -591761623739448166L;

	private Long userId;
	private String userEmailId;
	private String userName;
	private UserRole userRole;
	private List<Address> shippingAddresses;
	private UserState userState;
	private LocalDateTime createdOn;

	public User() {
	}

	public User(Long userId, String userEmailId, String userName, UserRole userRole, List<Address> shippingAddresses,
			UserState userState, LocalDateTime createdOn) {
		this.userId = userId;
		this.userEmailId = userEmailId;
		this.userName = userName;
		this.userRole = userRole;
		this.shippingAddresses = shippingAddresses;
		this.userState = userState;
		this.createdOn = createdOn;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public List<Address> getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(List<Address> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
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
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
