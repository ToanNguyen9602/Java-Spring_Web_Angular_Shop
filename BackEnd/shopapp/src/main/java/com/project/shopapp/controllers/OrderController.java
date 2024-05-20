package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

  @PostMapping("")
  public ResponseEntity<?> createOrder (@Valid @RequestBody OrderDTO orderDTO,
                                        BindingResult result) {
    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      return ResponseEntity.ok("Order created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{user_id}")
  public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId ) {
    try {
      return ResponseEntity.ok("Lấy ra danh sách order từ user_id");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  //công việc của admin
  public ResponseEntity<?> updateOrder (@Valid @RequestBody OrderDTO orderDTO,
                                        @Valid @PathVariable Long id) {
    return ResponseEntity.ok("Cập nhật thông tin 1 order");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteOrder(@Valid @PathVariable Long id) {
    //xoá mềm => cập nhật trường active = false
    return ResponseEntity.ok("Xoá 1 order thành công");
  }
}
