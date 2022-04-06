package com.example.demo.data;

import com.example.demo.model.dto.requests.CreateUserRequest;

public class UserData {
    public static CreateUserRequest createUserRequest(String username, String password) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(username);
        createUserRequest.setPassword(password);
        createUserRequest.setConfirmPassword(password);
        return createUserRequest;
    }
}
