package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data @Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductImageDTO {

  @JsonProperty("product_id")
  @Min(value = 1, message = "Product's ID must be greater than 0")
  private Long productId;

  @Size(min = 5, max = 200, message = " Image's name must be between 5 and 200 characters")
  @JsonProperty("image_url")
  private String imageUrl;
}
