package com.project.shopapp.controllers;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.shopapp.responses.OrderDetailResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor // open for all origins
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
  private final OrderDetailService orderDetailService;
  private final LocalizationUtils localizationUtils;
  //Thêm mới 1 order_detail
  @PostMapping
  public ResponseEntity<?> createOrderDetail (
    @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
    try {
      OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
      return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(newOrderDetail));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  //Lấy ra 1 orderDetail từ 1 id của order_Detail
  @GetMapping("/{id}")
  public ResponseEntity<?> getOrderDetail (
    @Valid @PathVariable("id") Long id) throws DataNotFoundException {
    OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
//    return ResponseEntity.ok(orderDetail);
    return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
  }

  //Lấy ra 1 orderDetail từ 1 id của 1 order id
  @GetMapping("/order/{orderId}")
  public ResponseEntity<?> getOrderDetails (@Valid @PathVariable("orderId") Long orderId) {
    List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
    List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
            .map(OrderDetailResponse::fromOrderDetail)
            .toList();
    return ResponseEntity.ok(orderDetailResponses);
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrderDetail (@Valid @PathVariable("id") Long id,
                                              @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
    try {
      OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
      return ResponseEntity.ok().body(orderDetail);
    } catch (DataNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOrderDetail (
    @Valid @PathVariable("id") Long id) {
    orderDetailService.deleteById(id);
    return ResponseEntity.ok().body("Detele orderDetail with Id" + id + "successfully");
  }


}
