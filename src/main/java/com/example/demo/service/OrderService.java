package com.example.demo.service;

import com.example.demo.mapper.UserOrderMapper;
import com.example.demo.model.dto.responses.UserOrderDTO;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public UserOrderDTO submit(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            logger.info("No user found for name " + username + ". Order not submitted.");
            return null;
        }
        UserOrder userOrder = orderRepository.save(UserOrder.createFromCart(user.getCart()));
        return UserOrderMapper.fromUserOrder(userOrder);
    }

    public List<UserOrderDTO> getOrdersForUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            logger.info("No user found for name " + username + ". Orders not retrieved.");
            return null;
        }
        List<UserOrder> orderList = orderRepository.findByUser(user);
        return orderList.stream().map(UserOrderMapper::fromUserOrder).collect(Collectors.toList());
    }
}
