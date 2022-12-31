package com.chakra.projects.investment;

import com.chakra.projects.investment.service.storage.StorageProperties;
import com.chakra.projects.investment.service.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(StorageProperties.class)
public class InvestmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService service) {
		return (args) -> {
			service.init();
		};
	}
}
