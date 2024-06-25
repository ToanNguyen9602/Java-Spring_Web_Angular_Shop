package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter @Setter
public class OrderResponse extends BaseResponse {
  private Long id;

  @JsonProperty("user_id")
  private Long userId;

  @JsonProperty("fullname")
  private String fullName;

  @JsonProperty("phone_number")
  private String phoneNumber;

  private String address;
  private String note;

  @JsonProperty("order_date")
  private LocalDateTime orderDate;

  private String status;

  @JsonProperty("total_money")
  private Float totalMoney;

  @JsonProperty("shipping_method")
  private String shippingMethod;

  @JsonProperty("payment_method")
  private String paymentMethod;

  @JsonProperty("shipping_address")
  private String shippingAddress;

  @JsonProperty("shipping_date")
  private String shippingDate;

  @JsonProperty("tracking_number")
  private String trackingNumber;

  @JsonProperty("active")
  private Boolean active; //thuộc về admin

}
