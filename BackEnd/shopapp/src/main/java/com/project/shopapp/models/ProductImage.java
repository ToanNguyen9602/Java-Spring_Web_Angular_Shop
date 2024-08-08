package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor @Builder
@Data @Getter @Setter @AllArgsConstructor
@Table(name = "product_images")
public class ProductImage {
  public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @JsonIgnore
  private Product product;

  @Column(name = "image_url", length = 300)
  @JsonProperty("image_url")
  private String imageURL;
}
