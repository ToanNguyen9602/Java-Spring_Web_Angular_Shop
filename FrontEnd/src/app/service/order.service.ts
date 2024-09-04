import { Injectable } from "@angular/core";
import { ProductService } from "./product.service";
import { environment } from "../environments/environment";
import { HttpClient } from "@angular/common/http";
import { OrderDTO } from "../dtos/order/order.dto";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class OrderService {
  private apiUrl = `${environment.apiBaseUrl}/orders`;

  constructor(private http: HttpClient) {}

  placeOrder(orderData: OrderDTO): Observable<any> {
    //gửi yêu cầu đặt hàng
    return this.http.post(this.apiUrl, orderData);
  }

  getOrderById(orderId: number): Observable<any> {
    const url = `${environment.apiBaseUrl}/orders/${orderId}`;
    return this.http.get(url);
  }
}
