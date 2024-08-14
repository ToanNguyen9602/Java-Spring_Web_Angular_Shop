package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidParamException;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;
import com.project.shopapp.responses.ProductResponse;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service @Builder
public class ProductService implements IProductService{
  private final ProductRepository productRespository;
  private final CategoryRespository categoryRespository;
  private final ProductImageRepository productImageRepository;
  @Override
  @Transactional
  public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
      Category existingCategory = categoryRespository.findById(productDTO.getCategoryId())
        .orElseThrow(() -> new DataNotFoundException(
          "Cannot find Category with Id: " + productDTO.getCategoryId()));

      Product newProduct = Product.builder()
        .name(productDTO.getName())
        .price(productDTO.getPrice())
        .thumbnail(productDTO.getThumbnail())
        .category(existingCategory)
        .description(productDTO.getDescription())
        .build();
      return productRespository.save(newProduct);
  }

//  @Override
//  public Product getProductById(Long productId) throws Exception {
//    return productRespository.findById(productId).orElseThrow(() ->
//      new DataNotFoundException("Cannot find Product with Id: " + productId));
//  }

  @Override
  public Product getProductById(long productId) throws Exception {
    Optional<Product> optionalProduct = productRespository.getDetailProduct(productId);
    if(optionalProduct.isPresent()) {
      return optionalProduct.get();
    }
    throw new DataNotFoundException("Cannot find product with id =" + productId);
  }

  @Override
  public List<Product> findProductsByIds(List<Long> productIds) {
    return productRespository.findProductsByIds(productIds);
  }

  @Override
  public Page<ProductResponse> getAllProducts(String keyword, long categoryId, PageRequest pageRequest) {
    //lấy danh sách sản phẩm theo page và limit và categoryId nếu có
    //chuyển từ danh sách product sang danh sách productResponse
    Page<Product> productsPage;
    productsPage = productRespository.searchProducts(categoryId, keyword, pageRequest);
    return productsPage.map(ProductResponse::fromProduct);
  }

  @Override
  @Transactional
  public Product updateProduct(Long productId, ProductDTO productDTO) throws Exception {
    Product existingProduct = getProductById(productId);
    existingProduct.setName(productDTO.getName());
    if (existingProduct != null) {
      Category existingCategory = categoryRespository.findById(productDTO.getCategoryId())
        .orElseThrow(() -> new DataNotFoundException(
          "Cannot find Category with Id: " + productDTO.getCategoryId()));
      existingProduct.setName(existingProduct.getName());
      existingProduct.setCategory(existingCategory);
      existingProduct.setPrice(productDTO.getPrice());
      existingProduct.setThumbnail(productDTO.getThumbnail());
      existingProduct.setDescription(productDTO.getDescription());
      return productRespository.save(existingProduct);
    }
    return null;
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    //productRespository.deleteById(id);
    Optional<Product> optionalProduct = productRespository.findById(id);
      optionalProduct.ifPresent(productRespository::delete);
  }

  @Override
  public boolean existsByName(String name) {
    return productRespository.existsByName(name);
  }
  @Override
  public ProductImage createProductImage (
    Long productId, ProductImageDTO productImageDTO) throws Exception {

    Product existingProduct = productRespository
      .findById(productId)
      .orElseThrow(() ->
        new DataNotFoundException("Cannot find product with Id: "
          + productImageDTO.getProductId()));

    ProductImage newProductImage = ProductImage.builder()
      .product(existingProduct)
            .imageURL(productImageDTO.getImageUrl())
      .build();

    //Kh cho insert quá 5 ảnh cho 1 sản phẩm
    int size = productImageRepository.findByProductId(productId).size();
    if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
      throw new InvalidParamException("Number of images must be " +
        "<= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
    }
    return productImageRepository.save(newProductImage);
  }
}
