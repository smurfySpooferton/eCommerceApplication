package com.example.demo.controllers;

import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.service.HashService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final HashService hashService;
	private final UserService userService;

	public UserController(HashService hashService, UserService userService) {
		this.hashService = hashService;
		this.userService = userService;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO userDTO = userService.byId(id);
		return userDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDTO);
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> findByUserName(@PathVariable String username) {
		UserDTO userDTO = userService.byUserName(username);
		return userDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userDTO);
	}

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try {
			UserDTO userDTO = userService.createUser(createUserRequest);
			return userDTO == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(userDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}
}
