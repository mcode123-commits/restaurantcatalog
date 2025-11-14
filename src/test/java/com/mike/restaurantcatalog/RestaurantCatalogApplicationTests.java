package com.mike.restaurantcatalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RestaurantCatalogApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("h2 db being used test is green now context loaded successfully");
	}

}
