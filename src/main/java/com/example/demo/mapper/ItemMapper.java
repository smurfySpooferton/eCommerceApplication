package com.example.demo.mapper;

import com.example.demo.model.dto.responses.ItemDTO;
import com.example.demo.model.persistence.Item;

public class ItemMapper {
    public static Item fromDto(ItemDTO itemDTO) {
        Item item = new Item();
        item.setPrice(itemDTO.getPrice());
        item.setDescription(itemDTO.getDescription());
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        return item;
    }

    public static ItemDTO fromItem(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        return itemDTO;
    }
}
