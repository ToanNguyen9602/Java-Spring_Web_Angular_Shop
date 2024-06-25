package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class OrderService implements IOrderService {
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
  @Override
  public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
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
    return null;
  }
  @Override
  public OrderResponse getOrder(Long id) {
    return null;
  }
  @Override
  public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
    return null;
  }
  @Override
  public void deleteOrder(Long id) {
  }
  @Override
  public List<OrderResponse> getAllOrders(Long userId) {
    return null;
  }
}
