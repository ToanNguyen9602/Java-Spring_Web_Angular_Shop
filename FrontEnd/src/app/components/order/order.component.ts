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
    userId: 1,
    // fullname: '',  Khởi tạo rỗng, sẽ được điền từ form
    email: "",
    phone_number: "",
    address: "",
    note: "",
    total_money: 0,
    payment_method: "cod",
    shipping_method: "express",
    coupon_code: "",
    cart_items: [],
    fullname: "",
  };
  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private formBuilder: FormBuilder,
    private tokenService: TokenService
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
    // Lấy danh sách sản phẩm từ giỏ hàng
    const cart = this.cartService.getCart();
    // Chuyển danh sách ID từ Map giỏ hàng
    const productIds = Array.from(cart.keys());
    debugger;
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
        console.log("done");
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
      // this.orderData.fullname = this.orderForm.get('fullname')!.value;
      // this.orderData.email = this.orderForm.get('email')!.value;
      // this.orderData.phone_number = this.orderForm.get('phone_number')!.value;
      // this.orderData.address = this.orderForm.get('address')!.value;
      // this.orderData.note = this.orderForm.get('note')!.value;
      // this.orderData.shipping_method = this.orderForm.get('shipping_method')!.value;
      // this.orderData.payment_method = this.orderForm.get('payment_method')!.value;

      this.orderData = {
        ...this.orderData,
        ...this.orderForm.value,
      };
      this.orderData.cart_items = this.cartItems.map((cartItem) => ({
        product_id: cartItem.product.id,
        quantity: cartItem.quantity,
      }));
    }
    //dữ liệu hợp lệ, gửi order đi
    this.orderService.placeOrder(this.orderData).subscribe({
      next: (response: Order) => {
        debugger;
        console.log("Đặt hàng thành công");
      },
      complete: () => {
        debugger;
        this.calculateTotal();
      },
      error: (error: any) => {
        debugger;
        console.error("Lỗi khi đặt hàng: ", error);
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
