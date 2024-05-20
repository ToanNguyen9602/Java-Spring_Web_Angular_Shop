package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
  @Column(name = "created_at")
  private LocalDateTime createAt;

  @Column(name = "updated_at")
  private LocalDateTime updateAt;

  @PrePersist
  public void onCreate() {
    createAt = LocalDateTime.now();
    updateAt = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    updateAt = LocalDateTime.now();
  }

}
