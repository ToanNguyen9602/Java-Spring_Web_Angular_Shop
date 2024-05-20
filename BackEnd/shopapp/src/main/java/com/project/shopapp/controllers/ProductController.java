package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.services.IProductService;
import com.project.shopapp.services.ProductService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
  private final IProductService productService;
  @GetMapping("")
  public ResponseEntity<String> getProducts(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
  ) {
    return ResponseEntity.ok("getProducts Here");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getProductById(
    @PathVariable("id") String productId
  ) {
    return ResponseEntity.ok("Product with ID: " + productId);
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
  public ResponseEntity<?> uploadImages(Long productId,
    @ModelAttribute("files") List<MultipartFile> files) {
    try {
      Product existingProduct = productService.getProductById(productId);
      files = files == null ? new ArrayList<MultipartFile>() : files;
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
        ProductImage productImage = productService.createProductImage(existingProduct.getId(),
          new ProductImageDTO().builder()
            .imageUrl(fileName).build());
        productImages.add(productImage);
      }
      return ResponseEntity.ok().body(productImages);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage()); //)
    }

  }

  private String storeFile(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
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

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(
    @PathVariable long id
  ) {
    return ResponseEntity.status(HttpStatus.OK).body("Product Deleted successfully");
  }
}
