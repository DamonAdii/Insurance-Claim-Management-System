package com.icms;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Insurance Claim Management System REST API Documentation",
				description = "Insurance Claim Management System REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Aditya Singh",
						email = "chauhan775386@gmail.com",
						url = "swagger-ui/index.html"
				),
				license = @License(
						name = "Apache 2.0",
						url = "swagger-ui/index.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Insurance Claim Management System REST API Documentation",
				url = "swagger-ui/index.html"
		)
)
public class InsuranceClaimManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceClaimManagementSystemApplication.class, args);
	}

}
