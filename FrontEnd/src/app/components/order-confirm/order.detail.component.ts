import { Component, OnInit } from "@angular/core";
import { environment } from "src/app/environments/environment";
import { Product } from "src/app/models/product";
import { CartService } from "src/app/service/cart.service";
import { ProductService } from "src/app/service/product.service";

@Component({
  selector: "app-order-confirm",
  templateUrl: "./order.detail.component.html",
  styleUrls: ["./order.detail.component.scss"],
})
export class OrderDetailComponent implements OnInit {
  cartItems: { product: Product; quantity: number }[] = [];
  totalAmount: number = 0;
  couponCode: string = "";

  constructor(
    private cartService: CartService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    //lấy danh sách sản phẩm từ trong giỏ hàng
    debugger;
    const cart = this.cartService.getCart();
    //chuyển  danh sách Id từ Map sang giỏ hàng
    const productIds = Array.from(cart.keys());
    console.log(productIds);
    //gọi service lấy thông tin sản phẩm dựa trên danh sách ID
    debugger;
    this.productService.getProductsByIds(productIds).subscribe({
      next: (products) => {
        debugger;
        console.log("products1 next: " + products);
        //lấy thông tin sp và so luong từ danh sách sp và giỏ hàng
        this.cartItems = productIds.map((productId) => {
          debugger;
          const product = products.find((p) => p.id === productId);
          if (product) {
            product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          }
          return {
            product: product!,
            quantity: cart.get(productId)!,
          };
        });
        console.log("oke done");
      },
      complete: () => {
        debugger;
        this.calculateTotal();
      },
      error: (error: any) => {
        debugger;
        console.error("Error fetching detail: ", error);
      },
    });
  }

  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  applyCoupon(): void {}
}
