package io.theam.crmservice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.theam.crmservice.api.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ApiCrmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCrmServiceApplication.class, args);
	}
}
