package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import jakarta.persistence.*;
import lombok.*;

@Data @Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor

public class OrderDetailResponse {
  private Long id;

  @JsonProperty("order_id")
  private Long orderId;

  @JsonProperty("product_id")
  private Long productId;

  @JsonProperty("price")
  private Float price;

  @JsonProperty("number_of_products")
  private int numberOfProducts;

  @JsonProperty("total_money")
  private Float totalMoney;

  private String Color;

  public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
    return OrderDetailResponse.builder()
       .id(orderDetail.getId())
       .orderId(orderDetail.getOrder().getId())
       .productId(orderDetail.getProduct().getId())
       .price(orderDetail.getPrice())
       .numberOfProducts(orderDetail.getNumberOfProducts())
       .totalMoney(orderDetail.getTotalMoney())
       .Color(orderDetail.getColor())
       .build();
  }
}
