package com.mike.restaurantcatalog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Disabled in CI – requires real DB configuration")
class RestaurantCatalogApplicationTests {

	@Test
	void contextLoads() {
	}

}
