import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: "root",
})
export class TokenService {
  private readonly TOKEN_KEY = "access_token";
  private jwtHelperService = new JwtHelperService();
  constructor() {}
  //getter
  getToken(): string {
    return localStorage.getItem(this.TOKEN_KEY) ?? "";
  }
  //setter
  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }
  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
  getUserId(): number {
    //lấy ra token
    let userObject = this.jwtHelperService.decodeToken(this.getToken() ?? "");
    //kiểm tra trong token có trường userId trong claims hay k
    return "userId" in userObject ? parseInt(userObject["userId"]) : 0;
  }
  isTokenExpired(): boolean {
    if (this.getToken() == null) {
      return false;
    }
    return this.jwtHelperService.isTokenExpired(this.getToken()!);
  }
}
