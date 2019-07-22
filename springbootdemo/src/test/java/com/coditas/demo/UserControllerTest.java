package com.coditas.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.coditas.demo.controller.UserController;
import com.coditas.demo.service.UserProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@WebMvcTest(value = UserController.class, secure = false)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@ComponentScan(basePackages = { "com.coditas" })
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc = null;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Mock
	private UserProjectService userService;

	@Test
	public void testUserController() throws Exception {
		mockMvc.perform(get("/user?userName=in28minutes")).andExpect(status().isOk());
	}

}
