import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { OrderDTO } from "src/app/dtos/order/order.dto";
import { environment } from "src/app/environments/environment";
import { Order } from "src/app/models/order";
import { Product } from "src/app/models/product";
import { CartService } from "src/app/service/cart.service";
import { OrderService } from "src/app/service/order.service";
import { ProductService } from "src/app/service/product.service";
import { TokenService } from "src/app/service/token.service";
import { Router, ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-order",
  templateUrl: "./order.component.html",
  styleUrls: ["./order.component.scss"],
})
export class OrderComponent implements OnInit {
  orderForm: FormGroup;
  cartItems: { product: Product; quantity: number }[] = [];
  couponCode: string = "";
  totalAmount: number = 0;
  orderData: OrderDTO = {
    user_id: 13,
    fullname: "",
    email: "",
    phone_number: "",
    address: "",
    note: "",
    total_money: 0,
    payment_method: "cod",
    shipping_method: "express",
    coupon_code: "",
    cart_items: [],
  };
  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private formBuilder: FormBuilder,
    private tokenService: TokenService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.orderForm = this.formBuilder.group({
      fullname: ["", Validators.required],
      email: ["", [Validators.email]],
      phone_number: ["", [Validators.required, Validators.minLength(6)]],
      address: ["", [Validators.required, Validators.minLength(5)]],
      note: [""],
      shipping_method: [""],
      payment_method: [""],
    });
  }

  ngOnInit(): void {
    debugger;
    // this.cartService.clearCart();
    this.orderData.user_id = this.tokenService.getUserId();
    // Lấy danh sách sản phẩm từ giỏ hàng
    const cart = this.cartService.getCart();
    // Chuyển danh sách ID từ Map giỏ hàng
    const productIds = Array.from(cart.keys());
    debugger;
    if (productIds.length === 0) {
      return;
    }
    //get info detail product by list productIds
    this.productService.getProductsByIds(productIds).subscribe({
      next: (products) => {
        debugger;
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

  placeOrder() {
    debugger;
    if (this.orderForm.valid) {
      this.orderData = {
        ...this.orderData,
        ...this.orderForm.value,
      };
      this.orderData.cart_items = this.cartItems.map((cartItem) => ({
        product_id: cartItem.product.id,
        quantity: cartItem.quantity,
      }));
      this.orderData.total_money = this.totalAmount;
    }
    //dữ liệu hợp lệ, gửi order đi
    this.orderService.placeOrder(this.orderData).subscribe({
      next: (response: Order) => {
        debugger;
        alert("Order placed successfully!");
        this.cartService.clearCart();
        this.router.navigate(["/"]);
      },
      complete: () => {
        debugger;
        this.calculateTotal();
      },
      error: (error: any) => {
        debugger;
        alert(`Lỗi khi đặt hàng: ${error}`);
      },
    });
  }

  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  // Hàm xử lý việc áp dụng mã giảm giá
  applyCoupon(): void {
    // Viết mã xử lý áp dụng mã giảm giá ở đây
    // Cập nhật giá trị totalAmount dựa trên mã giảm giá nếu áp dụng
  }
}
