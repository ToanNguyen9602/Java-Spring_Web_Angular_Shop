import { Component, ViewChild } from "@angular/core";
import { LoginDTO } from "../dtos/user/login.dto";
import { UserService } from "../service/user.service";
import { Router } from "@angular/router";
import { NgForm } from "@angular/forms";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent {
  @ViewChild("loginForm") loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  constructor(private router: Router, private userService: UserService) {
    this.phoneNumber = "0916855344";
    this.password = "string123";
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
      next: (response: any) => {
        debugger;
        // this.router.navigate(["/login"]);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        //xử lý lỗi nếu có
        alert(`Cannot login, error: ${error.error}`);
        debugger;
        console.error("Dang nhap khong thanh cong", error);
      },
    });
  }
}
