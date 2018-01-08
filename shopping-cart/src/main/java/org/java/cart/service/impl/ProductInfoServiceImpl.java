package org.java.cart.service.impl;

import java.util.Set;

import org.java.cart.constants.Messages;
import org.java.cart.domain.ProductInfo;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.ProductNotFoundException;
import org.java.cart.service.ProductInfoService;
import org.java.cart.utils.DataGenerator;
import org.java.cart.utils.Utils;

public class ProductInfoServiceImpl implements ProductInfoService {

	private volatile static ProductInfoServiceImpl _instance;
	private static Set<ProductInfo> products;

	private ProductInfoServiceImpl() {
	}

	public static ProductInfoServiceImpl getInstance() {
		if (_instance == null) {
			synchronized (ProductInfoServiceImpl.class) {
				if (_instance == null) {
					_instance = new ProductInfoServiceImpl();
					populateProducts();
				}
			}
		}
		return _instance;
	}

	private static void populateProducts() {
		setProducts(DataGenerator.getProductsInfoData());
	}

	public static void setProducts(Set<ProductInfo> products) {
		ProductInfoServiceImpl.products = products;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ProductService#addProduct(org.java.cart.domain.
	 * Product)
	 */
	@Override
	public boolean addProduct(ProductInfo product) {
		if (Utils.isNull(product)) {
			throw new InvalidInputException(Messages.INVALID_PRODUCT_ERROR_MSG);
		}
		return products.add(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ProductService#getProductByCode(java.lang.String)
	 */
	@Override
	public ProductInfo getProductByCode(String code) {
		if (Utils.isNull(code) || code.isEmpty()) {
			throw new InvalidInputException(Messages.INVALID_PRODUCT_CODE_ERROR_MSG);
		}

		ProductInfo product = findProductByCode(code);
		if (Utils.isNull(product)) {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND_ERROR_MSG);
		}
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.ProductService#getAllProducts()
	 */
	@Override
	public Set<ProductInfo> getAllProducts() {
		if (products.isEmpty()) {
			throw new ProductNotFoundException(Messages.PRODUCTS_NOT_FOUND_ERROR_MSG);
		}
		return products;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.java.cart.service.ProductService#removeProduct(java.lang.String)
	 */
	@Override
	public boolean removeProduct(String code) {
		if (Utils.isNull(code) || code.isEmpty()) {
			throw new InvalidInputException(Messages.INVALID_PRODUCT_CODE_ERROR_MSG);
		}

		ProductInfo product = Utils.findAndRemove(products, p -> p.getCode().equals(code));
		if (Utils.isNull(product)) {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND_ERROR_MSG);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.java.cart.service.ProductService#updateProduct(org.java.cart.domain.
	 * Product)
	 */
	@Override
	public ProductInfo updateProduct(ProductInfo product) {
		if (Utils.isNull(product)) {
			throw new InvalidInputException(Messages.INVALID_PRODUCT_ERROR_MSG);
		}

		ProductInfo findProductByCode = findProductByCode(product.getCode());
		if (Utils.isNull(findProductByCode)) {
			throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND_ERROR_MSG);
		}
		findProductByCode.setName(product.getName());
		findProductByCode.setDescription(product.getDescription());
		findProductByCode.setManufacturer(product.getManufacturer());
		findProductByCode.setPrice(product.getPrice());
		return findProductByCode;
	}

	/**
	 * Method to find the product using product code
	 * 
	 * @param code
	 * @return
	 */
	private ProductInfo findProductByCode(String code) {
		return products.stream().filter(p -> p.getCode().equals(code)).findAny().orElse(null);
	}

}
