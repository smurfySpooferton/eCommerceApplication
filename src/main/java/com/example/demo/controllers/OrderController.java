package com.example.demo.controllers;

import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrderDTO> submit(@PathVariable String username) {
        UserOrderDTO userOrderDTO = orderService.submit(username);
        if (userOrderDTO == null) {
            logger.info("Did not add order for user" + username + ".");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Successfully added order.");
            return ResponseEntity.ok(userOrderDTO);
        }
    }

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrderDTO>> getOrdersForUser(@PathVariable String username) {
        List<UserOrderDTO> userOrderDTOList = orderService.getOrdersForUser(username);
        if (userOrderDTOList == null) {
            logger.info("Could not find history for user " + username + ".");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Successfully retrieved order history for user " + username + ".");
            return ResponseEntity.ok(userOrderDTOList);
        }
    }
}
