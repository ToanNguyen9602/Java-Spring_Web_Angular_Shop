import { Component, OnInit, ViewChild } from "@angular/core";
import { LoginDTO } from "../../dtos/user/login.dto";
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";
import { NgForm } from "@angular/forms";
import { LoginResponse } from "src/app/responses/user/login.response";
import { TokenService } from "src/app/service/token.service";
import { RoleService } from "src/app/service/role.service";
import { Role } from "src/app/dtos/user/role";
import { UserResponse } from "src/app/responses/user/user.response";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  @ViewChild("loginForm") loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  roles: Role[] = [];
  rememberMe: boolean = true;
  selectedRole: Role | undefined;
  userResponse?: UserResponse;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) {
    this.phoneNumber = "0916855344";
    this.password = "string123";
  }

  ngOnInit() {
    debugger;
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger;
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      error: (error: any) => {
        debugger;
        console.error("Error getting roles: ", error);
      },
    });
  }

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  login() {
    const message = `phone: ${this.phoneNumber}` + `password: ${this.password}`;
    debugger;

    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1,
    };

    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        // let token = response.token;
        //muốn sử dụng token trong các yêu cầu API
        const { token } = response;
        if (this.rememberMe) this.tokenService.setToken(token);
        debugger;
        this.userService.getUserDetail(token).subscribe({
          next: (response: any) => {
            debugger;
            this.userResponse = {
              ...response,
              date_of_birth: new Date(response.date_of_birth),
            };
            this.userService.saveUserToLocalStorage(this.userResponse);
            this.router.navigate(["/"]);
          },
          complete: () => {
            debugger;
          },
          error: (error: any) => {
            debugger;
            alert(error.error.message);
          },
        });
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        alert(error.error.message);
      },
    });
  }
}
