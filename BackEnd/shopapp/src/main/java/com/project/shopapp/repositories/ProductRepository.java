package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <Product, Long> {
  boolean existsByName(String name);

  //PHÂN TRANG
  Page<Product> findAll(Pageable pageable);

  @Query("select p from Product p where " +
          "(:categoryId IS NULL OR :categoryId = 0 OR " +
          "p.category.id = :categoryId) AND (:keyword IS NULL OR " +
          ":keyword = '' OR p.name LIKE %:keyword%" +
          "OR p.description LIKE %:keyword%)")
  Page<Product> searchProducts (@Param("categoryId") Long categoryId,
                                @Param("keyword") String keyword, Pageable pageable);

  @Query("select p from Product p left join FETCH p.productImages where p.id = :productId")
  Optional<Product> getDetailProduct(@Param("productId") Long productId);

  @Query("select p from Product p WHERE p.id IN :productIds")
  List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
}
