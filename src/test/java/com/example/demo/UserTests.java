package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserController userController;

    @Test
    public void testUser() {
        CreateUserRequest createUserRequest = createUserRequest();
        UserDTO userDTO1 = userController.createUser(createUserRequest).getBody();

        Assertions.assertEquals(userDTO1.getUsername(), createUserRequest.getUsername());
        Assertions.assertTrue(userDTO1.getId() > 0);

        UserDTO userDTO2 = userController.findByUserName("Tester").getBody();
        UserDTO userDTO3 = userController.findById(userDTO1.getId()).getBody();

        Assertions.assertEquals(userDTO1.getId(), userDTO2.getId());
        Assertions.assertEquals(userDTO1.getUsername(), userDTO2.getUsername());
        Assertions.assertEquals(userDTO1.getId(), userDTO3.getId());
        Assertions.assertEquals(userDTO1.getUsername(), userDTO3.getUsername());

        HttpStatus status1 = userController.findById(userDTO1.getId() + 1).getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, status1);

        HttpStatus status2 = userController.findByUserName(userDTO1.getUsername() + "bla").getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, status2);
    }

    private CreateUserRequest createUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Tester");
        createUserRequest.setPassword("TestPw");
        return createUserRequest;
    }

}