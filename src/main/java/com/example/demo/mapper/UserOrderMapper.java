package com.example.demo.mapper;

import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

import java.util.List;
import java.util.stream.Collectors;

public class UserOrderMapper {
    public static UserOrderDTO fromUserOrder(UserOrder userOrder) {
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setUserId(userOrder.getUser().getId());
        userOrderDTO.setItems(userOrder.getItems().stream().map(Item::getId).collect(Collectors.toList()));
        userOrderDTO.setTotal(userOrder.getTotal());
        userOrderDTO.setId(userOrder.getId());
        return userOrderDTO;
    }

    public static UserOrder fromDto(UserOrderDTO userOrderDTO, User user, List<Item> items) {
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setItems(items);
        userOrder.setTotal(userOrderDTO.getTotal());
        userOrder.setId(userOrderDTO.getId());
        return userOrder;
    }
}
