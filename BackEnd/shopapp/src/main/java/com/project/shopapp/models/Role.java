package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;

@Entity
@Table(name = "roles")
@Data @Getter @Builder @Setter
@AllArgsConstructor @NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  public static String ADMIN = "ADMIN";
  public static String USER = "USER";
}
