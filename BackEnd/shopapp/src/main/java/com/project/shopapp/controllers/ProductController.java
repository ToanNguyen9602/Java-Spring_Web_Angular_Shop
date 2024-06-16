package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.IProductService;
import com.project.shopapp.services.ProductService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor

public class ProductController {
  private final IProductService productService;

  @GetMapping("")
  public ResponseEntity<ProductListResponse> getProducts(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
  ) {
    PageRequest pageRequest = PageRequest.of(page, limit,
      Sort.by("createAt").descending());
    Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
    int totalPages = productPage.getTotalPages();
    List<ProductResponse> products = productPage.getContent();
    return ResponseEntity.ok(ProductListResponse
      .builder()
      .products(products)
      .totalPages(totalPages)
      .build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(
    @PathVariable("id") Long productId
  ) throws Exception {
    Product existingproduct = productService.getProductById(productId);
    return ResponseEntity.ok(ProductResponse.fromProduct(existingproduct));
  }

  @PostMapping("")
  public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                          BindingResult result
  ) {
    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      Product newProduct = productService.createProduct(productDTO);
      return ResponseEntity.ok(newProduct);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = "uploads/{id}",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId,
    @ModelAttribute("files") List<MultipartFile> files) {
    try {
      Product existingProduct = productService.getProductById(productId);
      files = files == null ? new ArrayList<MultipartFile>() : files;
      if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File to large. Maximum 5 images");
      }
      List<ProductImage> productImages  = new ArrayList<>();
      for (MultipartFile file : files) {

        if (file.getSize() == 0) {
          continue;
        }

        //kiem tra kich thuoc và định dạng
        if (file.getSize() > 10 * 1024 * 1024) {
          return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File to large. Maximum 10MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
          return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
        }
        //lưu file và cập nhật thumbnail trong DTO
        String fileName = storeFile(file);
        //lưu vào đối tượng product trong DB
        ProductImage productImage = productService.createProductImage(
          existingProduct.getId(),
          ProductImageDTO.builder()
            .imageUrl(fileName).build());
        productImages.add(productImage);
      }
      return ResponseEntity.ok().body(productImages);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage()); //)
    }

  }

  private String storeFile(MultipartFile file) throws IOException {
    if (!isImageFile(file) && file.getOriginalFilename() != null) {
      throw new IOException("Invalid image format");
    }

    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    //thêm  UUID vào tên file để tạo tên duy nhât
    String uniqueFilename = UUID.randomUUID() + "_" + fileName;
    java.nio.file.Path uploadDir = Paths.get("uploads");
    //kiem tra và tạo thư mục nếu nó k tồn tại
    if (!Files.exists(uploadDir)) {
      Files.createDirectories(uploadDir);
    }
    //đường dẫn đầy đủ đén file
    java.nio.file.Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
    //Chép file vào thư mục đích
    Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
    return uniqueFilename;
  }

  private boolean isImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      return false;
    }
    return true;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(
    @PathVariable long productId
  ) {
    try {
      productService.deleteProduct(productId);
      return ResponseEntity.ok(String.format("Product with id %d was deleted: " + productId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

//  @PostMapping("/generateFakerProducts")
  private ResponseEntity<String> generateFakerProducts() {
    Faker faker = new Faker();

    for(int i = 0; i < 10_000; i++) {
      String productName = faker.commerce().productName();
      if(productService.existsByName(productName)) {
        continue;
      }
      ProductDTO productDTO = ProductDTO.builder()
        .name(productName)
        .price((float) faker.number().numberBetween(100, 100000))
        .description(faker.lorem().sentence())
        .thumbnail("")
        .categoryId((long) faker.number().numberBetween(2, 9))
        .build();
      try {
        productService.createProduct(productDTO);
      } catch (DataNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body("Faker products generated successfully");
  }
}
