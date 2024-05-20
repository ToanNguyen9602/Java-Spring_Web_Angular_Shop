package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "provider", length = 50, nullable = false)
  private String provider;

  @Column(name = "provider_id", length = 50)
  private String providerId;

  @Column(name = "name", length = 150)
  private Long name;

  @Column(name = "email", length = 150)
  private String email;

}
