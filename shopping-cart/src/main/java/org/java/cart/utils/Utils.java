package org.java.cart.utils;

import java.util.Iterator;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.commons.math3.util.Precision;
import org.apache.log4j.Logger;
import org.java.cart.constants.Constants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	final static Logger logger = Logger.getLogger(Utils.class);

	/**
	 * No-Args private constructor
	 * <p>
	 * It avoid creation of the instances of Utils class
	 */
	private Utils() {
		// No-Args private constructor
	}

	/**
	 * Method to generate UUID
	 * 
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Method to generate Long ID
	 * 
	 * @return
	 */
	public static Long generateLongID() {
		return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}

	/**
	 * Method to convert the Object to JSON string
	 * 
	 * @param object
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String convertObjectToJSONStr(Object object) {
		String jsonStr = Constants.EMPTY_STR;
		if (Utils.isNull(object))
			return jsonStr;

		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return jsonStr;
	}

	/**
	 * Method to find and remove a element from collection
	 * 
	 * @param collection
	 * @param predicate
	 * @return
	 */
	public static <T> T findAndRemove(Iterable<? extends T> collection, Predicate<? super T> predicate) {
		T value = null;
		for (Iterator<? extends T> it = collection.iterator(); it.hasNext();)
			if (predicate.test(value = it.next())) {
				it.remove();
				return value;
			}
		return null;
	}

	/**
	 * Method to round of the provided decimal places
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		return Precision.round(value, places);
	}

	/**
	 * Utility method for null check
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null ? true : false;
	}

	/**
	 * Utility method for not null check
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null ? true : false;
	}

	/**
	 * Method to calcualte the tax amount
	 * 
	 * @param productAmount
	 * @param tax
	 * @return
	 */
	public static double calculateTax(double productAmount, double tax) {
		return (tax * productAmount) / 100.0;
	}

}
