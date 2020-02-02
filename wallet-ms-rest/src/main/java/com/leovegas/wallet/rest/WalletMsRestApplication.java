package com.leovegas.wallet.rest;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.leovegas.wallet.core.WalletMsCore;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WalletMsRestApplication {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.leovegas.wallet.rest.controller"))
				.paths(PathSelectors.ant("/wallet/*")).build()
				.globalOperationParameters(Arrays.asList(
						new ParameterBuilder().name("sr_userId").description("userId header parameter")
								.modelRef(new ModelRef("string")).parameterType("header").required(true).build(),
						new ParameterBuilder().name("sr_locale").description("locale header parameter")
								.modelRef(new ModelRef("string")).parameterType("header").required(false).build()))
				.apiInfo(new ApiInfo("Wallet Microservice", "Wallet Microsevice API Documentation.", "REST", "",
						new Contact("Kadir Derer", "https://github.com/kderer", "kderer@hotmail.com"), "", "",
						Collections.emptyList()));
	}

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { WalletMsRestApplication.class, WalletMsCore.class }, args);
	}

}
