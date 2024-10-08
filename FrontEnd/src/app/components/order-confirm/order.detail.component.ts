import { Component, OnInit } from "@angular/core";
import { environment } from "src/app/environments/environment";
import { OrderDetail } from "src/app/models/order.detail";
import { Product } from "src/app/models/product";
import { OrderResponse } from "src/app/responses/order/order.response";
import { CartService } from "src/app/service/cart.service";
import { OrderService } from "src/app/service/order.service";
import { ProductService } from "src/app/service/product.service";

@Component({
  selector: "app-order-detail",
  templateUrl: "./order.detail.component.html",
  styleUrls: ["./order.detail.component.scss"],
})
export class OrderDetailComponent implements OnInit {
  orderResponse: OrderResponse = {
    id: 0,
    user_id: 13,
    fullname: "",
    phone_number: "",
    email: "",
    address: "",
    note: "",
    order_date: new Date(),
    status: "",
    total_money: 0,
    shipping_method: "",
    shipping_address: "",
    shipping_date: new Date(),
    payment_method: "",
    order_details: [],
  };

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.getOrderDetails();
    // //lấy danh sách sản phẩm từ trong giỏ hàng
    // debugger;
    // const cart = this.cartService.getCart();
    // //chuyển  danh sách Id từ Map sang giỏ hàng
    // const productIds = Array.from(cart.keys());
    // console.log(productIds);
    // //gọi service lấy thông tin sản phẩm dựa trên danh sách ID
    // debugger;
    // this.productService.getProductsByIds(productIds).subscribe({
    //   next: (products) => {
    //     debugger;
    //     console.log("products1 next: " + products);
    //     //lấy thông tin sp và so luong từ danh sách sp và giỏ hàng
    //     this.cartItems = productIds.map((productId) => {
    //       debugger;
    //       const product = products.find((p) => p.id === productId);
    //       if (product) {
    //         product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
    //       }
    //       return {
    //         product: product!,
    //         quantity: cart.get(productId)!,
    //       };
    //     });
    //     console.log("oke done");
    //   },
    //   complete: () => {
    //     debugger;
    //     this.calculateTotal();
    //   },
    //   error: (error: any) => {
    //     debugger;
    //     console.error("Error fetching detail: ", error);
    //   },
    // });
  }

  // calculateTotal(): void {
  //   this.totalAmount = this.cartItems.reduce(
  //     (total, item) => total + item.product.price * item.quantity,
  //     0
  //   );
  // }

  // applyCoupon(): void {}

  getOrderDetails(): void {
    debugger;
    const orderId = 56;
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {
        debugger;
        this.orderResponse.id = response.id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.fullname = response.fullname;
        this.orderResponse.email = response.email;
        this.orderResponse.address = response.address;
        this.orderResponse.note = response.note;
        this.orderResponse.order_date = new Date(
          response.order_date[0],
          response.order_date[1] - 1,
          response.order_date[2]
        );
        this.orderResponse.order_details = response.order_details.map(
          (order_detail: OrderDetail) => {
            order_detail.product.thumbnail = `${environment.apiBaseUrl}/products/images/${order_detail.product.thumbnail}`;
            return order_detail;
          }
        );
        this.orderResponse.payment_method = response.payment_method;
        this.orderResponse.shipping_date = new Date(
          response.order_date[0],
          response.order_date[1] - 1,
          response.order_date[2]
        );
        this.orderResponse.shipping_method = response.shipping_method;
        this.orderResponse.status = response.status;
        this.orderResponse.total_money = response.total_money;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error("Error fetching detail:", error);
      },
    });
  }
}
