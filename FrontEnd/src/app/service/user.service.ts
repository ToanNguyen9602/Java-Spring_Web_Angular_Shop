import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { ResgisterDTO } from "../dtos/user/register.dto";
import { LoginDTO } from "../dtos/user/login.dto";
import { environment } from "../environments/environment";
import { UserResponse } from "../responses/user/user.response";
import { UpdateUserDTO } from "../dtos/user/update.user.dto";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;
  private apiConfig = {
    headers: this.createHeaders(),
  };
  constructor(private http: HttpClient) {}

  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      "Content-Type": "application/json",
      "Accept-Language": "vi",
    });
  }

  register(registerDTO: ResgisterDTO): Observable<any> {
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }

  getUserDetail(token: string) {
    //post(url,body)
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      }),
    });
  }

  saveUserToLocalStorage(userResponse?: UserResponse) {
    debugger;
    try {
      if (userResponse == null || !userResponse) {
        return;
      }
      //convert userResponse object to a JSON string
      const userResponseJSON = JSON.stringify(userResponse);
      //Save thhe JSON string to local storage wutg a key
      localStorage.setItem("user", userResponseJSON);
      console.log("User response saved to local storage.");
    } catch (error) {
      console.log("Error saving user response to local storage");
    }
  }

  getUserResponseFromLocalStorage() {
    try {
      //getUser from Local Storage
      const userResponseJSON = localStorage.getItem("user");
      if (userResponseJSON == null || userResponseJSON == undefined) {
        return null;
      }
      //parse JSON back to Object
      const userResponse = JSON.parse(userResponseJSON!);
      return userResponse;
    } catch (error) {
      console.error("Error rtrieving user response from local storage");
      return null;
    }
  }

  removeUserFromLocalStorage(): void {
    try {
      localStorage.removeItem("user");
      console.log("User data remove from local storage");
    } catch (error) {
      console.error("Error removing user data from local storage");
    }
  }

  updateUserDetail(token: string, updateUserDTO: UpdateUserDTO) {
    debugger;
    let userResponse = this.getUserResponseFromLocalStorage();
    return this.http.put(
      `${this.apiUserDetail}/${userResponse?.id}`,
      updateUserDTO,
      {
        headers: new HttpHeaders({
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        }),
      }
    );
  }
}
