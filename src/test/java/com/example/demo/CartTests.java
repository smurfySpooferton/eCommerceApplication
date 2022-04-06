package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.data.ModifyCartRequestData;
import com.example.demo.model.dto.requests.ModifyCartRequest;
import com.example.demo.model.dto.responses.CartDTO;
import com.example.demo.model.dto.responses.UserDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CartTests extends AbstractAuthableTest {
    @Autowired
    private CartController cartController;

    public CartTests() {
        super("CartTests", "12345678");
    }

    @Test
    public void testCart() {
        UserDTO userDTO = addUser();
        ModifyCartRequestData.makeRequest(userDTO.getUsername());
        ModifyCartRequest request = ModifyCartRequestData.makeRequest(userDTO.getUsername());
        ResponseEntity<CartDTO> responseEntity = cartController.addTocart(request);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1L, responseEntity.getBody().getId());
        Assertions.assertEquals(2, responseEntity.getBody().getItemIds().size());
        Assertions.assertEquals(1L, responseEntity.getBody().getItemIds().get(0));
        request.setQuantity(1);
        responseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1L, responseEntity.getBody().getId());
        Assertions.assertEquals(1, responseEntity.getBody().getItemIds().size());
        Assertions.assertEquals(1L, responseEntity.getBody().getItemIds().get(0));

        request.setItemId(0);
        responseEntity = cartController.addTocart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        responseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        request.setItemId(1);
        request.setUsername(userDTO.getUsername() + "1");
        responseEntity = cartController.addTocart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        responseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
