package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.*;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public interface IProductService {
  Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
  Product getProductById(Long id) throws Exception;
  Page<ProductResponse> getAllProducts(PageRequest pageRequest);
  public Product updateProduct(Long id, ProductDTO productDTO) throws Exception;
  void deleteProduct(Long id);
  boolean existsByName(String name);

  ProductImage createProductImage (
    Long productId, ProductImageDTO productImageDTO) throws Exception;

}
