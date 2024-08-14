import { Injectable } from "@angular/core";
import { environment } from "../environments/environment";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";

import { Category } from "../models/category";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CategoryService {
  private apiGetCategories = `${environment.apiBaseUrl}/categories`;
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

  getCategories(page: number, limit: number): Observable<Category[]> {
    const params = new HttpParams()
      .set("page", page.toString())
      .set("limit", limit.toString());
    return this.http.get<Category[]>(this.apiGetCategories, { params });
  }
}
