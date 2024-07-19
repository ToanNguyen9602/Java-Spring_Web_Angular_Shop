import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { ResgisterDTO } from "../dtos/user/register.dto";
import { LoginDTO } from "../dtos/user/login.dto";
import { environtment } from "../environments/environment";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private apiRegister = `${environtment.apiBaseUrl}/users/register`;
  private apiLogin = `${environtment.apiBaseUrl}/users/login`;
  private apiConfig = {
    headers: this.createHeaders(),
  };
  constructor(private http: HttpClient) {}

  private createHeaders(): HttpHeaders {
    return new HttpHeaders({ "Content-Type": "application/json" });
  }

  register(registerDTO: ResgisterDTO): Observable<any> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    const headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    return this.http.post(this.apiRegister, loginDTO, this.apiConfig);
  }
}
