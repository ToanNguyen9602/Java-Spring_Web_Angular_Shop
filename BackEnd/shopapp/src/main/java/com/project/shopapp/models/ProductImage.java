package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @Builder
@Data @Getter @Setter @AllArgsConstructor
@Table(name = "product_images")
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "image_url", length = 300)
  private String imageUrl;
}
