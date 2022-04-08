package com.example.demo.service;

import com.example.demo.mapper.ItemMapper;
import com.example.demo.model.dto.responses.ItemDTO;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> all() {
        List<ItemDTO> list = itemRepository.findAll().stream().map(ItemMapper::fromItem).collect(Collectors.toList());
        logger.info("Found " + list.size() + " items.");
        return list;
    }

    public ItemDTO byId(Long itemId) {
        logger.info("Found item for id" + itemId + ".");
        return itemRepository.findById(itemId).map(ItemMapper::fromItem).orElse(null);
    }

    public List<ItemDTO> byName(String itemName) {
        List<ItemDTO> list = itemRepository.findByName(itemName).stream().map(ItemMapper::fromItem).collect(Collectors.toList());
        logger.info("Found " + list.size() + " items for name " + itemName + ".");
        return list;
    }
}
