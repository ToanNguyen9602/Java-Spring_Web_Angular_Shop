import { Injectable } from "@angular/core";
import { environtment } from "../environments/environment";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { Product } from "../dtos/product/product";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ProductService {
  private apiGetProducts = `${environtment.apiBaseUrl}/products`;
  private apiConfig = {
    headers: this.createHeader(),
  };

  constructor(private http: HttpClient) {}
  private createHeader(): HttpHeaders {
    return new HttpHeaders({
      "Content-Type": "application/json",
      "Accept-Language": "vi",
    });
  }

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
}
