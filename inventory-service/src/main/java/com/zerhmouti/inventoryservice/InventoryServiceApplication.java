package com.zerhmouti.inventoryservice;

import com.zerhmouti.inventoryservice.repository.InventoryRepository;
import com.zerhmouti.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory= Inventory.builder()
					.quantity(11)
					.skuCode("Iphone_13")
					.build();
			inventoryRepository.save(inventory);
			Inventory inventory1= Inventory.builder()
					.quantity(3)
					.skuCode("Iphone_13_red")
					.build();
			inventoryRepository.save(inventory1);
		};
	}
}
