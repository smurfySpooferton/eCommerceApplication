package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.data.UserData;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.security.JwtTokenBuilder;
import com.example.demo.security.SecurityConstants;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public abstract class AbstractAuthableTest {
    protected final String username;
    protected final String password;

    @Autowired
    protected UserController userController;
    @Autowired
    protected MockMvc mvc;

    public AbstractAuthableTest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected UserDTO addUser() {
        CreateUserRequest createUserRequest = UserData.createUserRequest(username, password);
        return userController.createUser(createUserRequest).getBody();
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test")).andExpect(status().isForbidden());
    }

    protected String generateAuthToken() {
        String token = JwtTokenBuilder.createToken(username);
        Assertions.assertNotNull(token);
        return SecurityConstants.TOKEN_PREFIX + token;
    }
}
