package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class OrderService implements IOrderService {
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
  @Override
  public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
    //tìm userId xem có tồn tài không
    //Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
    User user = userRepository.findById(orderDTO.getUserId())
      .orElseThrow(()-> new DataNotFoundException
      ("User not found with id: " + orderDTO.getUserId()));
    //convert orderDTO => Order
    modelMapper.typeMap(OrderDTO.class, Order.class)
      .addMappings(mapper -> mapper.skip(Order::setId));
    Order order = new Order();
    modelMapper.map(orderDTO,order);
    order.setUser(user);
    order.setOrderDate(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);
    //Kiểm tra shipping date phải >= ngày hôm nay
    LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
    if (shippingDate.isBefore(LocalDate.now())) {
      throw new DataNotFoundException("Date must be at least today!");
    }
    order.setShippingDate(shippingDate);
    order.setActive(true);
    orderRepository.save(order);
    return order;
  }
  @Override
  public Order getOrder(Long id) {
    return orderRepository.findById(id).orElse(null);
  }
  @Override
  public Order updateOrder(Long id, OrderDTO orderDTO)
          throws DataNotFoundException {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("Cannot find with Id: " + id));
    User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() ->
            new DataNotFoundException("Cannot find User with id " + id));
    modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
    modelMapper.map(orderDTO, order);
    order.setUser(existingUser);
    orderRepository.save(order);
    return null;
  }
  @Override
  public void deleteOrder(Long id) {
  }
  @Override
  public List<Order> findByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }
}
