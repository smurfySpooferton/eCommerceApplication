package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.requests.CreateUserRequest;
import com.example.demo.model.dto.responses.UserDTO;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final HashService hashService;

    public UserService(CartRepository cartRepository, UserRepository userRepository, HashService hashService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.hashService = hashService;
    }

    public UserDTO createUser(CreateUserRequest createUserRequest) {
        try {
            return UserMapper.fromUser(userRepository.save(storeUser(createUserRequest)));
        } catch (Exception e) {
            return null;
        }
    }

    private User storeUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        //user.setPassword(hashService.getHashedValue(createUserRequest.getPassword(), user.getSalt()));
        user.setPassword(createUserRequest.getPassword());
        return user;
    }

    public UserDTO byUserName(String username) {
        try {
            return UserMapper.fromUser(userRepository.findByUsername(username));
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

    public String getSalt(String username) {
        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            user = new User();
        }
        return user.getSalt();
    }
}
