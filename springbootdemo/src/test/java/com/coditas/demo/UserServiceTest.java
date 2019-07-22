package com.coditas.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.coditas.demo.model.Project;
import com.coditas.demo.service.UserProjectService;

@RunWith(SpringJUnit4ClassRunner.class)

public class UserServiceTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	UserProjectService userProjectService;

	@Test
	public void testfetchUserProjectswithgithubProject() throws Exception {

		String userName = "rajeshTambe";

		String s = "{\r\n" + "    \"total_count\": 199,\r\n" + "    \"incomplete_results\": false,\r\n"
				+ "    \"items\": [\r\n" + "        {\r\n" + "            \"id\": 41966461,\r\n"
				+ "            \"node_id\": \"MDEwOlJlcG9zaXRvcnk0MTk2NjQ2MQ==\",\r\n"
				+ "            \"name\": \"MockitoIn28Minutes\",\r\n"
				+ "            \"full_name\": \"in28minutes/MockitoIn28Minutes\",\r\n"
				+ "            \"private\": false\r\n" + "        }\r\n" + "    ]\r\n" + "        \r\n" + "}\r\n"
				+ "    ";

		ResponseEntity<String> list1 = new ResponseEntity<>(s, HttpStatus.OK);

		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), eq(null), eq(String.class)))
				.thenReturn(list1);
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(list1);

		List<Project> actual = userProjectService.fetchUserProjects(userName);

		assertEquals(actual.size(), 1);
		assertEquals(actual.get(0).getSource(), "Github");
		verify(restTemplate, times(2)).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class),
				eq(String.class));
		verifyNoMoreInteractions(restTemplate);

	}

	@Test
	public void testfetchUserProjectswithgitlabgithubProject() throws Exception {

		String userName = "rajeshTambe";

		String gitlab = " [\r\n" + "    {\r\n" + "        \"id\": 41966461,\r\n"
				+ "        \"node_id\": \"MDEwOlJlcG9zaXRvcnk0MTk2NjQ2MQ==\",\r\n"
				+ "        \"name\": \"MockitoIn28Minutes\",\r\n"
				+ "        \"full_name\": \"in28minutes/MockitoIn28Minutes\",\r\n" + "        \"private\": false\r\n"
				+ "    }\r\n" + "]\r\n" + "        \r\n" + "    ";

		String github = "{\r\n" + "    \"total_count\": 199,\r\n" + "    \"incomplete_results\": false,\r\n"
				+ "    \"items\": [\r\n" + "        {\r\n" + "            \"id\": 41966461,\r\n"
				+ "            \"node_id\": \"MDEwOlJlcG9zaXRvcnk0MTk2NjQ2MQ==\",\r\n"
				+ "            \"name\": \"MockitoIn28Minutes\",\r\n"
				+ "            \"full_name\": \"in28minutes/MockitoIn28Minutes\",\r\n"
				+ "            \"private\": false\r\n" + "        }\r\n" + "    ]\r\n" + "        \r\n" + "}\r\n"
				+ "    ";

		ResponseEntity<String> list1 = new ResponseEntity<>(github, HttpStatus.OK);
		ResponseEntity<String> list2 = new ResponseEntity<>(gitlab, HttpStatus.OK);

		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
				.thenReturn(list2);
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), eq(null), eq(String.class)))
				.thenReturn(list1);

		List<Project> actual = userProjectService.fetchUserProjects(userName);

		assertEquals(actual.size(), 2);

	}

}