package com.example.demo.mapper;

import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.model.persistence.User;

public class UserMapper {
    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
