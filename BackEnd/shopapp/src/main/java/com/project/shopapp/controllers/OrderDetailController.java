package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {

  //Thêm mới 1 order_detail
  @PostMapping
  public ResponseEntity<?> createOrderDetail (
    @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
    return ResponseEntity.ok("createOrderDetail");
  }
  //Lấy ra 1 orderDetail từ 1 id của order_Detail
  @GetMapping("/{orderId}")
  public ResponseEntity<?> getOrderDetail (
    @Valid @PathVariable("orderId") Long id) {
    return ResponseEntity.ok("getOrderDetail with ID: " + id);
  }

  //Lấy ra 1 orderDetail từ 1 id của 1 order id
  @GetMapping("/order/{orderId}")
  public ResponseEntity<?> getOrderDetails (@Valid @PathVariable("orderId") Long orderId) {
    return ResponseEntity.ok("getOrderDetails with ID: " + orderId);
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrderDetail (@Valid @PathVariable("id") Long id,
                                              @Valid @RequestBody OrderDetailDTO newOrderDetailData) {
    return ResponseEntity.ok("updateOrderDetail with ID: " + id +
      ", newOrderDetailData: " + newOrderDetailData);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderDetail (
    @Valid @PathVariable("id") Long id) {
    return ResponseEntity.noContent().build();
  }


}
