package org.java.cart.service;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.java.cart.constants.Constants;
import org.java.cart.constants.ProductCategory;
import org.java.cart.domain.ProductInfo;
import org.java.cart.exception.InvalidInputException;
import org.java.cart.exception.ProductNotFoundException;
import org.java.cart.service.impl.ProductInfoServiceImpl;
import org.java.cart.utils.Utils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductInfoServiceTest {
	private static ProductInfoService productInfoService;
	private static ProductInfo productInfo;
	private static String productCode;

	@BeforeClass
	public static void init() {
		productInfoService = ProductInfoServiceImpl.getInstance();

		productInfo = new ProductInfo(Utils.generateUUID(), "Nirma Beauty Soap", 45.60, "Nirma Soap is a beauty soap.",
				"Nirma Ltd.", ProductCategory.COSMATIC, LocalDateTime.now());

		productCode = productInfo.getCode();
	}

	@Test(expected = InvalidInputException.class)
	public void test10AddProductWithProductAsNull() {
		productInfoService.addProduct(null);
	}

	@Test
	public void test11AddProductWithPositiveScenario() {
		assertTrue(productInfoService.addProduct(productInfo));
	}

	@Test
	public void test12AddProductWithNegativeScenario() {
		assertFalse(productInfoService.addProduct(productInfo));
	}

	@Test(expected = InvalidInputException.class)
	public void test13GetProductByCodeWithCodeAsNull() {
		productInfoService.getProductByCode(null);
	}

	@Test(expected = InvalidInputException.class)
	public void test14GetProductByCodeWithCodeAsEmpty() {
		productInfoService.getProductByCode(Constants.EMPTY_STR);
	}

	@Test
	public void test15GetProductByCodeWithPositiveScenario() {
		ProductInfo productByCode = productInfoService.getProductByCode(productCode);
		assertEquals(productInfo, productByCode);
	}

	@Test(expected = ProductNotFoundException.class)
	public void test16GetProductByCodeWithNegativeScenario() {
		productInfoService.getProductByCode(Utils.generateUUID());
	}

	@Test
	public void test17GetAllProducts() {
		int expectedSize = 4;
		Set<ProductInfo> allProducts = productInfoService.getAllProducts();
		assertTrue(allProducts.size() == expectedSize);
	}

	@Test(expected = InvalidInputException.class)
	public void test18UpdateProductWithProductAsNull() {
		productInfoService.updateProduct(null);
	}

	@Test
	public void test19UpdateProductWithPositiveScenario() {
		String expectedProductName = "Nirma Extra Beauty Soap";
		productInfo.setName("Nirma Extra Beauty Soap");
		ProductInfo updateProduct = productInfoService.updateProduct(productInfo);
		assertTrue(productInfo == updateProduct);
		assertTrue(expectedProductName.equals(updateProduct.getName()));
	}

	@Test(expected = InvalidInputException.class)
	public void test20RemoveProductWithCodeAsNull() {
		productInfoService.removeProduct(null);
	}

	@Test(expected = InvalidInputException.class)
	public void test21RemoveProductWithCodeAsEmpty() {
		productInfoService.removeProduct(Constants.EMPTY_STR);
	}

	@Test
	public void test22RemoveProductWithPositiveScenario() {
		assertTrue(productInfoService.removeProduct(productCode));
	}

	@Test(expected = ProductNotFoundException.class)
	public void test23RemoveProductWithPositiveScenario() {
		productInfoService.removeProduct(Utils.generateUUID());
	}

}
