package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.data.ModifyCartRequestData;
import com.example.demo.data.UserData;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.requests.ModifyCartRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.util.CryptoHelper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTests {

    @Autowired
    private OrderController orderController;
    @Autowired
    private UserController userController;
    @Autowired
    private CartController cartController;

    @Test
    public void testOrder() {
        UserDTO userDTO = addUser();
        ModifyCartRequest request = ModifyCartRequestData.makeRequest(userDTO.getUsername());
        cartController.addTocart(request);
        ResponseEntity<UserOrderDTO> responseEntity = orderController.submit(request.getUsername());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<List<UserOrderDTO>> listResponseEntity = orderController.getOrdersForUser(request.getUsername());
        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        Assertions.assertEquals(1, listResponseEntity.getBody().size());
        Assertions.assertEquals(1, listResponseEntity.getBody().get(0).getItems().get(0));

        request.setUsername(CryptoHelper.makeSalt());
        responseEntity = orderController.submit(request.getUsername());
        listResponseEntity = orderController.getOrdersForUser(request.getUsername());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, listResponseEntity.getStatusCode());
    }

    private UserDTO addUser() {
        CreateUserRequest createUserRequest = UserData.createUserRequest();
        return userController.createUser(createUserRequest).getBody();
    }
}
