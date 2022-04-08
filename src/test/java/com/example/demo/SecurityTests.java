package com.example.demo;

import com.example.demo.security.JwtTokenBuilder;
import com.example.demo.security.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTests extends AbstractAuthableTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public SecurityTests() {
        super("SecurityTests", "12345678");
    }

    @Test
    public void testTokenBuilder() {
        Assertions.assertNotNull(JwtTokenBuilder.createToken("dude"));
    }

    @Test
    public void testUserDetails() {
        addUser();
        String userName = userDetailsService.loadUserByUsername("SecurityTests").getUsername();
        Assertions.assertEquals("SecurityTests", userName);
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        addUser();
        mvc.perform(MockMvcRequestBuilders.get("/api/order/history/" + username).header("Authorization", generateAuthToken())).andExpect(status().isOk());
    }
}
