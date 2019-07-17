package io.azar.pservice.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ValidationControllerIntegrationTest {

	private static final String LENGTH_ERROR_MSG = "Password should be minimum 5 and maximum 12 characters !";
	private static final String ALPHA_NUMERIC_ERROR_MSG = "Password must consist of a mixture of lower-case letters and numerical digits only, with at least one of each.";
	private static final String REPEATING_SEQUENCE_ERROR_MSG = "Password must not contain any sequence of characters immediately followed by the "
			+ "same sequence.";
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void lengthLessThanFiveTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"1234\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(LENGTH_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void lengthGreaterThanTwelveTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"123456789abcdff\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(LENGTH_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void missingDigitTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"abvce\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(ALPHA_NUMERIC_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void missingAlphabetTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"122345\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(ALPHA_NUMERIC_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void specialCharacterIncludedPasswordTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"@@$_pass12\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(ALPHA_NUMERIC_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	
	@Test
	public void repeatedSequenceTest1() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"abc12ac\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(REPEATING_SEQUENCE_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void repeatedSequenceTest2() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"rummy12my\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(REPEATING_SEQUENCE_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void repeatedSequenceTest3() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"12abcabc\"}"))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.content()
						.string(REPEATING_SEQUENCE_ERROR_MSG))
				.andReturn();
		// @formatter:on
	}
	
	@Test
	public void correctPasswordFormatTest() throws Exception {

		// @formatter:off
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/validatePassword").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"password\":\"password12\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content()
						.string("Password passed compliance test successfully."))
				.andReturn();
		// @formatter:on
	}

}
