package com.example.demo.controllers;

import com.example.demo.model.dto.requests.ModifyCartRequest;
import com.example.demo.model.dto.responses.CartDTO;
import com.example.demo.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private final CartService cartService;
	private final Logger logger = LoggerFactory.getLogger(CartController.class);

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/addToCart")
	public ResponseEntity<CartDTO> addTocart(@RequestBody ModifyCartRequest request) {
		CartDTO cartDTO = cartService.addToCart(request.getUsername(), request.getItemId(), request.getQuantity());
		if (cartDTO == null) {
			logger.info("Item with id" + request.getItemId() + " not added to cart.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Added item with id " + request.getItemId() + " to cart.");
		return ResponseEntity.ok(cartDTO);
	}

	@PostMapping("/removeFromCart")
	public ResponseEntity<CartDTO> removeFromcart(@RequestBody ModifyCartRequest request) {
		CartDTO cartDTO = cartService.removeFromcart(request.getUsername(), request.getItemId(), request.getQuantity());
		if (cartDTO == null) {
			logger.info("Item with id" + request.getItemId() + " not removed from cart.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Removed item with id " + request.getItemId() + " from cart.");
		return ResponseEntity.ok(cartDTO);
	}
}
