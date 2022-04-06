package com.example.demo;

import com.example.demo.data.UserData;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

public class UserTests extends AbstractAuthableTest {
    public UserTests() {
        super("UserTests", "12345678");
    }

    @Test
    public void testUser() {
        CreateUserRequest createUserRequest = UserData.createUserRequest(username, password);
        UserDTO userDTO1 = userController.createUser(createUserRequest).getBody();

        Assertions.assertEquals(userDTO1.getUsername(), createUserRequest.getUsername());
        Assertions.assertTrue(userDTO1.getId() > 0);

        UserDTO userDTO2 = userController.findByUserName("UserTests").getBody();
        UserDTO userDTO3 = userController.findById(userDTO1.getId()).getBody();

        Assertions.assertEquals(userDTO1.getId(), userDTO2.getId());
        Assertions.assertEquals(userDTO1.getUsername(), userDTO2.getUsername());
        Assertions.assertEquals(userDTO1.getId(), userDTO3.getId());
        Assertions.assertEquals(userDTO1.getUsername(), userDTO3.getUsername());

        HttpStatus status1 = userController.findById(userDTO1.getId() + 1).getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, status1);

        HttpStatus status2 = userController.findByUserName(userDTO1.getUsername() + "bla").getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, status2);

        createUserRequest.setPassword("12345677");
        Assertions.assertNull(userController.createUser(createUserRequest).getBody());

        createUserRequest.setPassword("1");
        createUserRequest.setConfirmPassword("1");
        Assertions.assertNull(userController.createUser(createUserRequest).getBody());
    }
}