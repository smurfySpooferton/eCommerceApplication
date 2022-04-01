package com.example.demo.mapper;

import com.example.demo.model.dto.responses.CartDTO;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;

import java.util.stream.Collectors;

public class CartMapper {
    public static CartDTO fromCart(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setItemIds(cart.getItems().stream().map(Item::getId).collect(Collectors.toList()));
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setId(cart.getId());
        cartDTO.setTotal(cart.getTotal());
        return cartDTO;
    }
}
