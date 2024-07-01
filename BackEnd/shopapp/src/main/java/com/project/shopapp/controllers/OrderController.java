package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
  private final IOrderService orderService;
  @PostMapping("")
  public ResponseEntity<?> createOrder (@Valid @RequestBody OrderDTO orderDTO,
                                        BindingResult result) {
    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      Order order = orderService.createOrder(orderDTO);
      return ResponseEntity.ok(order);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId ) {
    try {
      Order existingOrder = orderService.getOrder(orderId);
      return ResponseEntity.ok(existingOrder);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  //công việc của admin
  public ResponseEntity<?> updateOrder (@Valid @RequestBody OrderDTO orderDTO,
                                        @Valid @PathVariable Long id) {
    try {
      Order existingOrder = orderService.updateOrder(id, orderDTO);
      return ResponseEntity.ok(existingOrder);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
    //xoá mềm => cập nhật trường active = false
    return ResponseEntity.ok("Xoá 1 order thành công");
  }

  @GetMapping("user/{user_id}")
  public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId ) {
    try {
      List<Order> orders = orderService.findByUserId(userId);
      return ResponseEntity.ok(orders);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
