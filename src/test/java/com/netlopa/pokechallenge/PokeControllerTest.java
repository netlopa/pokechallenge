package com.netlopa.pokechallenge;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test of controller
 * Unfortunately I put only the NOT FOUND test because of the strict rate limiting of the Funtranslations API
 * @author lpavez
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PokeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/pokemon/dummymon").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}
}
