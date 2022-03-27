package com.example.demo.controllers;

import com.example.demo.model.dto.responses.ItemDTO;
import com.example.demo.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<List<ItemDTO>> getItems() {
		return ResponseEntity.ok(itemService.all());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
		ItemDTO itemDTO = itemService.byId(id);
		return itemDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(itemDTO);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<ItemDTO>> getItemsByName(@PathVariable String name) {
		return ResponseEntity.ok(itemService.byName(name));
	}
	
}
