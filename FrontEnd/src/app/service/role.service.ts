import { HttpClient } from "@angular/common/http";
import { environtment } from "../environments/environment";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class RoleService {
  private apiGetRoles = `${environtment.apiBaseUrl}/roles`;

  constructor(private http: HttpClient) {}
  getRoles(): Observable<any> {
    return this.http.get<any[]>(this.apiGetRoles);
  }
}
