package org.java.cart.utils;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.java.cart.constants.Constants;
import org.java.cart.domain.Address;
import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGenerateUUID() {
		assertNotNull("Result can not be null.", Utils.generateUUID());
	}

	@Test
	public void testGenerateLongID() {
		assertNotNull("Result can not be null.", Utils.generateLongID());
	}

	@Test
	public void testConvertObjectToJSONStr() {
		Address address = new Address("100 MAIN ST PO BOX 1022", "SEATTLE", "WA", "USA", "98104");
		assertNotNull("Result can not be null.", Utils.convertObjectToJSONStr(address));
	}

	@Test
	public void testConvertObjectToJSONStrForNullObject() {
		assertEquals("If null passed return empty string", Constants.EMPTY_STR, Utils.convertObjectToJSONStr(null));
	}

	@Test
	public void testFindAndRemoveForPositive() {
		Set<String> names = new LinkedHashSet<>();
		names.add("John");
		String name = Utils.findAndRemove(names, p -> p.equals("John"));
		assertTrue("John".equals(name));
	}

	@Test
	public void testFindAndRemoveForNegative() {
		Set<String> names = new LinkedHashSet<>();
		names.add("John");
		String name = Utils.findAndRemove(names, p -> p.equals("abc"));
		assertNull(name);
		assertFalse("John".equals(name));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRoundForPlaceLessThanZero() {
		Utils.round(10.45, -1);
	}

	@Test
	public void testRoundForPlaceEqualToZero() {
		double expected = 10.0;
		assertTrue(expected == Utils.round(10.45, 0));
	}

	@Test
	public void testRoundForPlaceToOneWithUpperRounding() {
		double expected = 10.5;
		assertTrue(expected == Utils.round(10.46, 1));
	}

	@Test
	public void testRoundForPlaceToOneWithLowerRounding() {
		double expected = 10.4;
		assertTrue(expected == Utils.round(10.44, 1));
	}

	@Test
	public void testRoundForNegativeCase() {
		double expected = 10.4;
		assertFalse(expected == Utils.round(10.45, 2));
	}

	@Test
	public void testIsNull() {
		assertTrue(Utils.isNull(null));
		assertFalse(Utils.isNull(new Object()));
	}

	@Test
	public void testIsNotNull() {
		assertTrue(Utils.isNotNull(new Object()));
		assertFalse(Utils.isNotNull(null));
	}

	@Test
	public void testCalculateTax() {
		double expected = 2.0;
		assertTrue(expected == Utils.calculateTax(20.0, 10.0));
	}

}
