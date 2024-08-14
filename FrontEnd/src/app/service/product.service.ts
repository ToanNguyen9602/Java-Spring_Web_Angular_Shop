import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Product } from "../models/product";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ProductService {
  private apiGetProducts = `${environment.apiBaseUrl}/products`;

  constructor(private http: HttpClient) {}

  getProducts(
    keyword: string,
    selectedCategoryId: number,
    page: number,
    limit: number
  ): Observable<Product[]> {
    const params = new HttpParams()
      .set("keyword", keyword)
      .set("category_id", selectedCategoryId.toString())
      .set("page", page.toString())
      .set("limit", limit.toString());
    return this.http.get<Product[]>(this.apiGetProducts, { params });
  }

  getDetailProduct(productId: number) {
    return this.http.get(`${environment.apiBaseUrl}/products/${productId}`);
  }

  getProductsByIds(productIds: number[]): Observable<Product[]> {
    //chuyển danh sách Id thành 1 chuỗi và truyền vào params
    debugger;
    const params = new HttpParams().set("ids", productIds.join(","));
    return this.http.get<Product[]>(`${this.apiGetProducts}/by-ids?`, {
      params,
    });
  }
}
