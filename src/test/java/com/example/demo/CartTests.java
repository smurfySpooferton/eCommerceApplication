package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.UserController;
import com.example.demo.data.ModifyCartRequestData;
import com.example.demo.data.UserData;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.requests.ModifyCartRequest;
import com.example.demo.model.dto.responses.CartDTO;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.util.CryptoHelper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartTests {
    @Autowired
    private CartController cartController;
    @Autowired
    private UserController userController;

    @Test
    public void testCart() {
        UserDTO userDTO = addUser();
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
        request.setUsername(CryptoHelper.makeSalt());
        responseEntity = cartController.addTocart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        responseEntity = cartController.removeFromcart(request);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private UserDTO addUser() {
        CreateUserRequest createUserRequest = UserData.createUserRequest();
        return userController.createUser(createUserRequest).getBody();
    }
}
