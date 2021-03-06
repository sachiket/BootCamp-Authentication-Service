package com.cg.iter.authenticationservice.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;



/****************************************************************************************************************************************
 * Class Name : SwaggerConfig <br>
 * Description : Configuring swagger docket for documentation.  <br>
 
 ****************************************************************************************************************************************/
@Configuration
public class SwaggerConfig {

	private final String basePackage = "com.cg.iter.authenticationservice";
	@Bean
	public Docket swaggerConfigurationAuth() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Authentication")
				.select()
				.paths(PathSelectors.ant("/app/auth/*"))
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.build()
				.apiInfo(apiDetails());
	}
	
	@Bean
	public Docket swaggerConfigurationAdmin() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Admin")
				.select()
				.paths(PathSelectors.ant("/app/admin/*"))
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.build()
				.apiInfo(apiDetails());
	}
	
	@Bean
	public Docket swaggerConfigurationMerchant() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Merchant")
				.select()
				.paths(PathSelectors.ant("/app/master/*"))
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.build()
				.apiInfo(apiDetails());
	}
	
	@Bean
	public Docket swaggerConfigurationUser() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("User")
				.select()
				.paths(PathSelectors.ant("/app/user/*"))
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.build()
				.apiInfo(apiDetails());
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiDetails() {
		return new ApiInfo(
				
				"Authentication API", 
				"Authenticate, add, delete and edit users", 
				"1.0", 
				"Free to use", 
				"Iter", 
				"", 
				"iter.com"
				
				);
	}
}
