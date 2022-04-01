package com.example.demo.data;

import com.example.demo.model.dto.requests.CreateUserRequest;

public class UserData {
    public static CreateUserRequest createUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Tester");
        createUserRequest.setPassword("TestPw");
        return createUserRequest;
    }
}
