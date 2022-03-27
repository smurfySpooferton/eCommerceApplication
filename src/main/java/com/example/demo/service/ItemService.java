package com.example.demo.service;

import com.example.demo.mapper.ItemMapper;
import com.example.demo.model.dto.responses.ItemDTO;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> all() {
        return itemRepository.findAll().stream().map(ItemMapper::fromItem).collect(Collectors.toList());
    }

    public ItemDTO byId(Long itemId) {
        return itemRepository.findById(itemId).map(ItemMapper::fromItem).orElse(null);
    }

    public List<ItemDTO> byName(String itemName) {
        return itemRepository.findByName(itemName).stream().map(ItemMapper::fromItem).collect(Collectors.toList());
    }
}
