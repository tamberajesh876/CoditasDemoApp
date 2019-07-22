package com.coditas.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coditas.demo.model.Project;
import com.coditas.demo.service.UserProjectService;

@RestController
public class UserController {

	@Autowired
	UserProjectService userProjectService;

	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Project> getUserProjects(@RequestParam("userName") String userName) {
		return userProjectService.fetchUserProjects(userName);
	}

}
