package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "price", nullable = false)
  private Float price;

  @Column(name = "number_of_products", nullable = false)
  private int numberOfProducts;

  @Column(name = "total_money", nullable = false)
  private Float totalMoney;

  private String Color;

}
