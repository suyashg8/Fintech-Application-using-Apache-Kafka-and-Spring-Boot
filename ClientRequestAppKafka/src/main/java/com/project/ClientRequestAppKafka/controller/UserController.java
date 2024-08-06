package com.project.ClientRequestAppKafka.controller;


import com.project.ClientRequestAppKafka.dto.UserDTO;
import com.project.ClientRequestAppKafka.exception.UserNotFoundException;
import com.project.ClientRequestAppKafka.models.UserData;
import com.project.ClientRequestAppKafka.repository.UserRepository;
import com.project.ClientRequestAppKafka.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserRepository userRepository;
	private final NotificationService notificationService;
	
    @Autowired
    public UserController(UserRepository userRepository, NotificationService notificationService) {
		super();
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}
	
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserDTO userDTO) {
    	userRepository.save(new UserData(userDTO.getUsername(),userDTO.getPassword(),userDTO.getFirstName(),userDTO.getLastName(),userDTO.getAge(),userDTO.getOccupation()));
    	notificationService.sendMessageThroughKafka(userDTO);
    }
    
	@PostMapping("/validate")
	public String validateTokenAndGetUsername(@RequestHeader("Authorization") String token) {
		return userRepository.findById(token).orElseThrow(UserNotFoundException::new).getUser_name();
		
	}

	
}
