package com.coditas.demo.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coditas.demo.model.Project;
import com.coditas.demo.model.ProjectMap;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserProjectService {

	@Autowired
	RestTemplate restTemplate;
	ObjectMapper mapper = new ObjectMapper();

	@Value("#{'${url}'.split(',')}")
	private List<String> url;

	@Value("${accesstoken}")
	private String accessToken1;

	final String githubProjectURI = "https://api.github.com/search/repositories?q=username";
	final String gitlabProjectURI = "https://gitlab.com/api/v4/users/username/projects";
	private String accessToken = "-KkByKHCxbJHVSp-scrH";

	public List<Project> fetchUserProjects(String userName) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		List<Project> gitHubProjects = fetchUserGitHubProjects(githubProjectURI.replace("username", userName));
		List<Project> gitLabProjects = fetchUserGitLabProjects(gitlabProjectURI.replace("username", userName));
		gitHubProjects.addAll(gitLabProjects);
		return gitHubProjects;
	}

	public List<Project> fetchUserGitHubProjects(String url) {
		try {
			ResponseEntity<String> projectList = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			ProjectMap map1 = mapper.readValue(projectList.getBody(), ProjectMap.class);
			map1.getItems().stream().forEach(project -> project.setSource("Github"));
			return map1.getItems();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public List<Project> fetchUserGitLabProjects(String url) {
		final HttpHeaders headers = new HttpHeaders();
		headers.set("PRIVATE-TOKEN", accessToken);
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			Project[] map2 = mapper.readValue(response.getBody(), Project[].class);
			Arrays.asList(map2).stream().forEach(project -> project.setSource("GitLab"));
			return Arrays.asList(map2);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}