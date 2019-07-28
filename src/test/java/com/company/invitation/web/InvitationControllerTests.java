package com.company.invitation.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class InvitationControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void processInvitationListTest() throws Exception {
		mockMvc.perform(get("/invitation/process"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Results match! Congratulations!"))
				;
	}
}