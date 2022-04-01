package com.example.demo.data;

import com.example.demo.model.dto.requests.ModifyCartRequest;

public class ModifyCartRequestData {
    public static ModifyCartRequest makeRequest(String username) {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setQuantity(2);
        request.setUsername(username);
        return request;
    }
}
