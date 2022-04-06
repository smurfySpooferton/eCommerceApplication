package com.example.demo.service;

import com.example.demo.mapper.CartMapper;
import com.example.demo.model.dto.responses.CartDTO;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public CartDTO addToCart(String username, Long itemId, int quantity) {
        User user = userRepository.findUserByUsername(username);
        Optional<Item> item = itemRepository.findById(itemId);
        if (user == null || !item.isPresent()) {
            return null;
        }
        Cart cart = user.getCart();
        cart.setUser(user);
        IntStream.range(0, quantity).forEach(i -> cart.addItem(item.get()));
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.fromCart(savedCart);
    }

    public CartDTO removeFromcart(String username, Long itemId, int quantity) {
        User user = userRepository.findUserByUsername(username);
        Optional<Item> item = itemRepository.findById(itemId);
        if (user == null || !item.isPresent()) {
            return null;
        }
        Cart cart = user.getCart();
        IntStream.range(0, quantity).forEach(i -> cart.removeItem(item.get()));
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.fromCart(savedCart);
    }
}
