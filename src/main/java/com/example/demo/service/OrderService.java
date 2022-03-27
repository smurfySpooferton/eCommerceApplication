package com.example.demo.service;

import com.example.demo.mapper.UserOrderMapper;
import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public UserOrderDTO submit(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        UserOrder userOrder = orderRepository.save(UserOrder.createFromCart(user.getCart()));
        return UserOrderMapper.fromUserOrder(userOrder);
    }

    public List<UserOrderDTO> getOrdersForUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        List<UserOrder> orderList = orderRepository.findByUser(user);
        return orderList.stream().map(UserOrderMapper::fromUserOrder).collect(Collectors.toList());
    }
}
