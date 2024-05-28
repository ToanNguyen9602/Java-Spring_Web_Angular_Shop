package com.project.shopapp.responses;

import lombok.*;
import java.util.List;

@AllArgsConstructor @Data @Builder
public class ProductListResponse {
  private List<ProductResponse> products;
  private int totalPages;
}
