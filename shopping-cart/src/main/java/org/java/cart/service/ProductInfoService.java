package org.java.cart.service;

import java.util.Set;

import org.java.cart.domain.ProductInfo;

public interface ProductInfoService {
	/**
	 * Method to add product to the system
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param product
	 * @return
	 */
	boolean addProduct(ProductInfo product);

	/**
	 * Method to get the product by the product code
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * <li>USER</li>
	 * </ul>
	 * 
	 * @param code
	 * @return
	 */
	ProductInfo getProductByCode(String code);

	/**
	 * Method to get all the products from the system *
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * <li>USER</li>
	 * </ul>
	 * 
	 * @return
	 */
	Set<ProductInfo> getAllProducts();

	/**
	 * Method to get the remove the product from the system
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param code
	 * @return
	 */
	boolean removeProduct(String code);

	/**
	 * Method to update the Product in the system
	 * <ul>
	 * ROLE to perform this operation
	 * <li>ADMIN</li>
	 * </ul>
	 * 
	 * @param product
	 * @return
	 */
	ProductInfo updateProduct(ProductInfo product);
}
