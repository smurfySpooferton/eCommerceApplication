package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.OrderController;
import com.example.demo.data.ModifyCartRequestData;
import com.example.demo.model.dto.requests.ModifyCartRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.model.dto.responses.UserOrderDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class OrderTests extends AbstractAuthableTest {

    @Autowired
    private OrderController orderController;
    @Autowired
    private CartController cartController;

    public OrderTests() {
        super("OrderTests", "12345678");
    }

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

        request.setUsername(userDTO.getUsername() + "1");
        responseEntity = orderController.submit(request.getUsername());
        listResponseEntity = orderController.getOrdersForUser(request.getUsername());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, listResponseEntity.getStatusCode());
    }
}
