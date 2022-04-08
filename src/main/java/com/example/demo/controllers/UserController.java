package com.example.demo.controllers;

import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO userDTO = userService.byId(id);
		if (userDTO == null) {
			logger.info("Could not find user for id " + id + ".");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("Successfully returned user for id " + id + ".");
			return ResponseEntity.ok(userDTO);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> findByUserName(@PathVariable String username) {
		UserDTO userDTO = userService.byUserName(username);
		if (userDTO == null) {
			logger.info("Could not find user for name " + username + ".");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("Successfully returned user for id " + username + ".");
			return ResponseEntity.ok(userDTO);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try {
			UserDTO userDTO = userService.createUser(createUserRequest);
			if (userDTO == null) {
				logger.info("Could not create user for name " + userDTO.getUsername() + ".");
				return ResponseEntity.badRequest().build();
			} else {
				logger.info("Successfully created user for name " + userDTO.getUsername() + ".");
				return ResponseEntity.ok(userDTO);
			}
		} catch (Exception e) {
			logger.error("Critical error! Could not create user.");
			return ResponseEntity.badRequest().build();
		}

	}
}
