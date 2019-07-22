package com.coditas.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

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
import com.coditas.demo.model.Project;
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

	@Test
	public void testuserProjects() throws Exception {

		String userName = "rajeshTambe";
		List<Project> list1 = new LinkedList<>();
		Project pr1 = new Project();
		pr1.setId(1);
		pr1.setName("project1");
		pr1.setSource("Github");
		Project pr2 = new Project();
		pr2.setId(2);
		pr2.setName("project2");
		list1.add(pr1);
		list1.add(pr2);

		List<Project> list2 = new LinkedList<>();
		Project pr3 = new Project();
		pr3.setId(1);
		pr3.setName("project3");
		Project pr4 = new Project();
		pr4.setId(2);
		pr4.setName("project4");
		list2.add(pr3);
		list2.add(pr4);

		when(userService.fetchUserGitHubProjects(userName)).thenReturn(list1);
		when(userService.fetchUserGitLabProjects(userName)).thenReturn(list2);

		List<Project> actual = new UserProjectService().fetchUserProjects(userName);

		assertEquals(actual.size(), list1.size() + list2.size());
		assertEquals(actual.get(0).getName(), list1.get(0).getName());
		assertEquals(actual.get(0).getSource(), list1.get(0).getName());
	}

}
