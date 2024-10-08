package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.*;
import com.project.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
  Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
//  Product getProductById(Long id) throws Exception;
Product getProductById(long productId) throws Exception;
  List<Product> findProductsByIds(List<Long> productIds);
  public Product updateProduct(Long id, ProductDTO productDTO) throws Exception;
  void deleteProduct(Long id);
  boolean existsByName(String name);
  ProductImage createProductImage (
    Long productId, ProductImageDTO productImageDTO) throws Exception;
  Page<ProductResponse> getAllProducts(String keyword, long categoryId, PageRequest pageRequest);
}
