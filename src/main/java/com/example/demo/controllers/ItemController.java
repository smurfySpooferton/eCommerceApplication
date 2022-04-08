package com.example.demo.controllers;

import com.example.demo.model.dto.responses.ItemDTO;
import com.example.demo.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

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
        if (itemDTO == null) {
            logger.info("Could not find item.");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Found item for id " + id + ".");
            return ResponseEntity.ok(itemDTO);
        }
    }

	@GetMapping("/name/{name}")
	public ResponseEntity<List<ItemDTO>> getItemsByName(@PathVariable String name) {
        logger.info("Retrieved items by name " + name + ".");
		return ResponseEntity.ok(itemService.byName(name));
	}
	
}
