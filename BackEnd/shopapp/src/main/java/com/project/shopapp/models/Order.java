package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Builder
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "fullname", length = 100)
  private String fullname;

  @Column(name = "phone_number", length = 100, nullable = false)
  private String phoneNumber;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "address", length = 100)
  private String address;

  @Column(name = "note", length = 100)
  private String note;

  @Column(name = "order_date")
  private LocalDate orderDate;

  @Column(name = "status")
  private String status;

  @Column(name = "total_money")
  private Float totalMoney;

  @Column(name = "shipping_method")
  private String shippingMethod;

  @Column(name = "shipping_address")
  private String shippingAddress;

  @Column(name = "shipping_date")
  private LocalDate shippingDate;

  @Column(name = "tracking_number")
  private String trackingNumber;

  @Column(name = "payment_method")
  private String paymentMethod;

//  @Column(name = "payment_status")
//  private String paymentStatus;
//
//  @Column(name = "payment_date")
//  private Date paymentDate;

  @Column(name = "active")
  private Boolean active;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<OrderDetail> orderDetails;

}
