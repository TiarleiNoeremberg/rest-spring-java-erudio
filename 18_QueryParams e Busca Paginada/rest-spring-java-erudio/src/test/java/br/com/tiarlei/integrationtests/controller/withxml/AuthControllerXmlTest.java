package br.com.tiarlei.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.tiarlei.configs.TestConfigs;
import br.com.tiarlei.integrationtests.dto.AccountCredentialDTO;
import br.com.tiarlei.integrationtests.dto.TokenDTO;
import br.com.tiarlei.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXmlTest  extends AbstractIntegrationTest{

	private static TokenDTO tokenDTO;
	
	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialDTO user = new AccountCredentialDTO("leandro", "admin123");
		
		tokenDTO = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post() 
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDTO.class);
		
		assertNotNull(tokenDTO.getAccessToken());
		assertNotNull(tokenDTO.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {

		var newTokenDTO = given()
				.basePath("/auth/refresh")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("username", tokenDTO.getUsername())
					.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
				.body()
				.as(TokenDTO.class);
		
		assertNotNull(newTokenDTO.getAccessToken());
		assertNotNull(newTokenDTO.getRefreshToken());
	}
}
