package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.net.ProtocolFamily;

@Entity
@Builder
@Data @Getter @Setter
@Table(name = "categories")
@AllArgsConstructor @NoArgsConstructor
public class Category {
  @Id //khai báo id vậy để biết nó là khoá chính
  @GeneratedValue(strategy = GenerationType.IDENTITY) //tự động tăng lên 1 - k co data nào giống nhau cả
  //@Column(name = "id")
  private Long id;
  @Column(name = "name",nullable = false)
  private String name;

}
