import { ProductService } from "src/app/service/product.service";
import { Injectable } from "@angular/core";
import { Product } from "../models/product";

@Injectable({
  providedIn: "root",
})
export class CartService {
  private cart: Map<number, number> = new Map();

  constructor(private productService: ProductService) {
    //lấy dữ liệu giỏ hàng từ localStorage khi khởi tạo service
    const storeCart = localStorage.getItem("cart");
    if (storeCart) {
      this.cart = new Map(JSON.parse(storeCart));
    }
  }

  addToCart(productId: number, quantity: number = 1): void {
    debugger;
    if (this.cart.has(productId)) {
      //nếu sản phẩm đã có trong giỏ hàng, tăng số lượng quantity lên
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      //Nếu sản phẩm chưa có trao giỏ hàng thì thêm sản phẩm vào số luongjw với quantity
      this.cart.set(productId, quantity);
    }
    //Sau khi thay đổi giỏ hàng, lưu trừ nó vào localStorage
    this.saveCartToLocalStorage();
  }

  getCart(): Map<number, number> {
    return this.cart;
  }
  private saveCartToLocalStorage(): void {
    debugger;
    localStorage.setItem(
      "cart",
      JSON.stringify(Array.from(this.cart.entries()))
    );
  }
  //xoá dữ liệu cart và cập nhật Local Storage
  clearCart(): void {
    this.cart.clear(); //xoá toàn bộ dữ liệu trong giỏ hàng
    this.saveCartToLocalStorage(); // lưu giỏ hàng vào localStorage trống
  }
}
