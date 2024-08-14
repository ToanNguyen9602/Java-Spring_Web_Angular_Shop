package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
  @Id //khai báo id vậy để biết nó là khoá chính
  @GeneratedValue(strategy = GenerationType.IDENTITY) //tự động tăng lên 1 - k co data nào giống nhau cả
  private Long id;

  @Column(name = "name",nullable = false, length = 350)
  private String name;

  private Float price;

  @Column(name = "thumbnail", length = 300)
  private String thumbnail;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "product",
          cascade = CascadeType.ALL,
          fetch = FetchType.LAZY)
  private List<ProductImage> productImages;
}
