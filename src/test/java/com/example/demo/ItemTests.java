package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.model.dto.responses.ItemDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTests {

    @Autowired
    private ItemController itemController;

    @Test
    public void testItems() {
        List<ItemDTO> byName = itemController.getItemsByName("Round Widget").getBody();
        Assertions.assertNotEquals(null, byName);
        Assertions.assertEquals(1, byName.size());

        ItemDTO byId = itemController.getItemById(byName.get(0).getId()).getBody();
        List<ItemDTO> all = itemController.getItems().getBody();

        Assertions.assertNotEquals(null, all);
        Assertions.assertTrue(all.size() > 0);
        for (ItemDTO item : all) {
            Assertions.assertTrue(item.getId() > 0);
        }

        Assertions.assertEquals(ItemMapper.fromDto(byName.get(0)), ItemMapper.fromDto(all.get(0)));
        Assertions.assertEquals(ItemMapper.fromDto(byId), ItemMapper.fromDto(all.get(0)));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, itemController.getItemById(99L).getStatusCode());
    }
}