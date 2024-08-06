package com.project.LendingEngineAppKafka.controller;

import com.project.LendingEngineAppKafka.model.User;
import com.project.LendingEngineAppKafka.service.TokenValidationService;
import com.project.LendingEngineAppKafka.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

	private final UserRepository userRepository;
	private final TokenValidationService tokenValidationService;
	
	@Autowired
	public UserController(UserRepository userRepository, TokenValidationService tokenValidationService) {
		super();
		this.userRepository = userRepository;
		this.tokenValidationService = tokenValidationService;
	}

	@GetMapping(value="/users")
	public List<User> findUsers(HttpServletRequest request) {
		tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
		return userRepository.findAll();
	}
}
