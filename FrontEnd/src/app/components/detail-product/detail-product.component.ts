import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Product } from "src/app/dtos/product/product";
import { ProductImage } from "src/app/dtos/product_image/product_image";
import { environtment } from "src/app/environments/environment";
import { CategoryService } from "src/app/service/category.service";
import { ProductService } from "src/app/service/product.service";

@Component({
  selector: "app-detail-product",
  templateUrl: "./detail-product.component.html",
  styleUrls: ["./detail-product.component.scss"],
})
export class DetailProductComponent implements OnInit {
  product?: Product;
  productId: number = 0;
  currentImageIndex: number = 0;
  constructor(
    private productService: ProductService // private categoryService: CategoryService,
  ) // private router: Router,
  // private activatedRoute: ActivatedRoute
  {}

  ngOnInit(): void {
    //lấy productId từ URL
    debugger;
    const idParam = 5;
    if (idParam !== null) {
      this.productId = +idParam;
    }
    if (!isNaN(this.productId)) {
      this.productService.getDetailProduct(this.productId).subscribe({
        next: (response:any) => {
          //Lấy danh sách sản phẩm và thay đổi URL
          if (response.product_images && response.product_images.length > 0) {
            response.product_images.forEach((product_image:ProductImage
              product_image.image_url = `${environtment.apiBaseUrl}/products/images/${product_image.image_url}`=> {
           
          });
        }
      })
    }
  }
}
