package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.util.Constants.MINIMUM_PASSWORD_LENGTH;

@Transactional
@Service
public class UserService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(CartRepository cartRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDTO createUser(CreateUserRequest createUserRequest) {
        if (createUserRequest.getPassword().length() < MINIMUM_PASSWORD_LENGTH
                || !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            //TODO: LOG IT
            return null;
        }
        try {
            return UserMapper.fromUser(userRepository.save(storeUser(createUserRequest)));
        } catch (Exception e) {
            //TODO: LOG IT
            return null;
        }
    }

    private User storeUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        return user;
    }

    public UserDTO byUserName(String username) {
        try {
            return UserMapper.fromUser(userRepository.findUserByUsername(username));
        } catch (Exception e) {
            return null;
        }
    }

    public UserDTO byId(Long id) {
        try {
            return UserMapper.fromUser(userRepository.getOne(id));
        } catch (Exception e) {
            return null;
        }
    }
}
