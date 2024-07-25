import { Component, ViewChild } from "@angular/core";
import { LoginDTO } from "../../dtos/user/login.dto";
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";
import { NgForm } from "@angular/forms";
import { LoginResponse } from "src/app/responses/login.response";
import { TokenService } from "src/app/service/token.service";
import { RoleService } from "src/app/service/role.service";
import { Role } from "src/app/dtos/user/role";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent {
  @ViewChild("loginForm") loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  roles: Role[] = [];
  rememberMe: boolean = true;
  selectedRole: Role | undefined;

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
    };

    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        // let token = response.token;
        //muốn sử dụng token trong các yêu cầu API
        const { token } = response;
        this.tokenService.setToken(token);
        // this.router.navigate(["/login"]);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        //xử lý lỗi nếu có
        alert(`Cannot login, error: ${error?.error?.message}`);
        debugger;
        console.error("Dang nhap khong thanh cong", error);
      },
    });
  }
}
