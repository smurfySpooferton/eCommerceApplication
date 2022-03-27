package com.example.demo.controllers;

import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrderDTO> submit(@PathVariable String username) {
		UserOrderDTO userOrderDTO = orderService.submit(username);
		return userOrderDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userOrderDTO);
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrderDTO>> getOrdersForUser(@PathVariable String username) {
		List<UserOrderDTO> userOrderDTOList = orderService.getOrdersForUser(username);
		return userOrderDTOList == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userOrderDTOList);
	}
}
