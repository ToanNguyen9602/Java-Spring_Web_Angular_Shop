package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
  OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;
  OrderResponse getOrder(Long id);
  OrderResponse updateOrder(Long id, OrderDTO orderDTO);
  void deleteOrder(Long id);
  List<OrderResponse> getAllOrders(Long userId);
}
