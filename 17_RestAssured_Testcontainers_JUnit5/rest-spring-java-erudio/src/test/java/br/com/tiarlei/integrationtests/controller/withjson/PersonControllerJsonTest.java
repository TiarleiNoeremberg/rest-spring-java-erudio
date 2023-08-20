package br.com.tiarlei.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tiarlei.configs.TestConfigs;
import br.com.tiarlei.data.dto.v1.security.TokenDTO;
import br.com.tiarlei.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.tiarlei.integrationtests.dto.AccountCredentialDTO;
import br.com.tiarlei.integrationtests.dto.PersonDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest{
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonDTO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonDTO();
	}

	@Test
	@Order(1)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialDTO user = new AccountCredentialDTO("leandro", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDTO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
		
		@Test
		@Order(2)
		public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TIARLEI)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		assertNotNull(createdPerson);
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		
		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Richard", createdPerson.getFirstName());
		assertEquals("Stallman", createdPerson.getLastName());
		assertEquals("New York City, New York, US", createdPerson.getAddress());
		assertEquals("Male", createdPerson.getGender());
	}
		
		@Test
		@Order(3)
		public void testFindById() throws JsonMappingException, JsonProcessingException {
			mockPerson();
				
			var content = given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_TIARLEI)
						.pathParam("id", person.getId())
						.when()
						.get("{id}")
					.then()
						.statusCode(200)
							.extract()
							.body()
								.asString();
			
			PersonDTO persistedPerson = objectMapper.readValue(content, PersonDTO.class);
			person = persistedPerson;
			
			assertNotNull(persistedPerson);
			
			assertNotNull(persistedPerson.getId());
			assertNotNull(persistedPerson.getFirstName());
			assertNotNull(persistedPerson.getLastName());
			assertNotNull(persistedPerson.getAddress());
			assertNotNull(persistedPerson.getGender());
			
			assertTrue(persistedPerson.getId() > 0);
			
			assertEquals("Richard", persistedPerson.getFirstName());
			assertEquals("Stallman", persistedPerson.getLastName());
			assertEquals("New York City, New York, US", persistedPerson.getAddress());
			assertEquals("Male", persistedPerson.getGender());
		}

	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City, New York, US");
		person.setGender("Male");		
	}
}
