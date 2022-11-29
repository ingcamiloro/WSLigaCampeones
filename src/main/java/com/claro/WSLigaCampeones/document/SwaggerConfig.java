package com.claro.WSLigaCampeones.document;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.basePackage("com.claro.WSLigaCampeones.servicio.ws.rest"))
//				.paths(PathSelectors.any()).build().apiInfo(apiInfoProducto()
//						);
//	}
//	
//
//	private ApiInfo apiInfoProducto() {
//		return new ApiInfoBuilder().title("Liga de Campeones")
//				.description("Servicio rest encargado de la transacciones del portal de liga de campeones, donde se pueden crear productos, redimir puntos y consultas de reportes ")
//				.version("0.0.1").build();
//	}
	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.any())
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo())
	            .securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Sig-Predict REST API Document")
	            .description("work in progress")
	            .termsOfServiceUrl("localhost")
	            .version("1.0")
	            .build();
	}

	private ApiKey apiKey() {
	    return new ApiKey("jwtToken", "Authorization", "header");
	}
}