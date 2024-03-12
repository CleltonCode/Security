package com.clelton.sct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void loginWithValidUserThenAuthenticated() throws Exception{
		FormLoginRequestBuilder loginFrom = formLogin()
				.user("user")
				.password("password");
		
		mockMvc.perform(loginFrom).andExpect(authenticated().withUsername("user"));
	}
	
	
	@Test
	void loginWithInvalidUserThenAuthenticated() throws Exception{
		FormLoginRequestBuilder loginForm = formLogin()
				.user("invalid")
				.password("password");
		
		mockMvc.perform(loginForm).andExpect(unauthenticated());			
	}
	
	@Test
	void accessUnsecuredResourceThenOk() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
	
	@Test
	void accessSecuredResourceUnauthenticatedThenRedirectToLogin() throws Exception{
		mockMvc.perform(get("/hello")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));
	}
	
	
	@Test
	@WithMockUser
	void accessSecuredResoucerAutenticatedThenOk() throws Exception {

		MvcResult mvcResult = mockMvc
					.perform(get("/hello"))
					.andExpect(status().isOk())
					.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).contains("Hello user");
	}
	

}


