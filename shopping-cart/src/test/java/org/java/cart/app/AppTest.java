package org.java.cart.app;

import org.java.cart.service.OrderServiceTest;
import org.java.cart.service.ProductInfoServiceTest;
import org.java.cart.service.ShoppingCartServiceTest;
import org.java.cart.service.UserServiceTest;
import org.java.cart.utils.UtilsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UtilsTest.class, UserServiceTest.class, ProductInfoServiceTest.class, OrderServiceTest.class,
		ShoppingCartServiceTest.class })

public class AppTest {
	// the class remains empty,
	// used only as a holder for the above annotations
}