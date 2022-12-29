package com.djoumatch.inventoryservice;

import com.djoumatch.inventoryservice.model.Inventory;
import com.djoumatch.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("i13");
			inventory.setQuantity(150);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("i13_pro");
			inventory2.setQuantity(15);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);

		};
	}
}
