package com.djoumatch.inventoryservice;

import com.djoumatch.inventoryservice.model.Inventory;
import com.djoumatch.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {


	@Container
	static private MySQLContainer mySQLContainer = new MySQLContainer("mysql:5");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository inventoryRepository;

	@DynamicPropertySource
	static void setDaynamisProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
	}

	@Test
	void shouldReturnTrue() throws Exception {
		Inventory inventory = new Inventory();
		inventory.setSkuCode("Iphone 13");
		inventory.setQuantity(15);

		inventoryRepository.save(inventory);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/" + inventory.getSkuCode()))
				.andExpect(MockMvcResultMatchers.content().string("true"));
	}

}
