package com.project.shopapp.services;

import com.project.shopapp.dtos.CartItemDTO;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
public class OrderService implements IOrderService {
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
  private final ProductRepository productRepository;
  private final OrderDetailRepository orderDetailRepository;

  @Override
  @Transactional
  public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
    //tìm userId xem có tồn tài không
    //Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
    User user = userRepository.findById(orderDTO.getUserId())
      .orElseThrow(()-> new DataNotFoundException
      ("User not found with id: " + orderDTO.getUserId()));
    //convert orderDTO => Order
    //dùng thư viện Model Mapper
    // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
    modelMapper.typeMap(OrderDTO.class, Order.class)
      .addMappings(mapper -> mapper.skip(Order::setId));
    Order order = new Order();
    modelMapper.map(orderDTO,order);
    order.setUser(user);
    order.setOrderDate(LocalDate.now());
    order.setStatus(OrderStatus.PENDING);
    //Kiểm tra shipping date phải >= ngày hôm nay
    LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
    if (shippingDate.isBefore(LocalDate.now())) {
      throw new DataNotFoundException("Date must be at least today!");
    }
    order.setShippingDate(shippingDate);
    order.setActive(true);
    order.setTotalMoney(orderDTO.getTotalMoney());
    orderRepository.save(order);

    //Tạo danh sách các đới tượng OrderDetail từ cartItem
    List<OrderDetail> orderDetails = new ArrayList<>();
    for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
      //Tạo 1 đối tượng OrderDetail từ CartItemDTO
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setOrder(order);

      //Lấy thông tin sản phẩm từ cartItemDTO
      Long productId = cartItemDTO.getProductId();
      int quantity = cartItemDTO.getQuantity();

      //Tìm thông tin sản phẩm từ cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
      Product product = productRepository.findById(productId).orElseThrow(() ->
        new DataNotFoundException("Product not found with id: " + productId));

      //Đặt thông tin cho OrderDetail
        orderDetail.setProduct(product);
        orderDetail.setNumberOfProducts(quantity);
        // Các trường khác của OrderDetail nếu cần
        orderDetail.setPrice(product.getPrice());
        //Thêm orderDetail vào danh sách
        orderDetails.add(orderDetail);
      }
    orderDetailRepository.saveAll(orderDetails);
    return order;
  }
  @Override
  public Order getOrder(Long id) {
    return orderRepository.findById(id).orElse(null);
  }
  @Override
  @Transactional
  public Order updateOrder(Long id, OrderDTO orderDTO)
          throws DataNotFoundException {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("Cannot find with Id: " + id));
    User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() ->
            new DataNotFoundException("Cannot find User with id " + id));
    modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
    modelMapper.map(orderDTO, order);
    order.setUser(existingUser);
    return orderRepository.save(order);
  }
  @Override
  @Transactional
  public void deleteOrder(Long id) {
    Order order = orderRepository.findById(id).orElse(null);
    //Khong xoa cứng, mà xóa mềm
    if (order != null) {
      order.setActive(false);
      orderRepository.save(order);
    }
  }
  @Override
  public List<Order> findByUserId(Long userId) {
    return orderRepository.findByUserId(userId);
  }
}
