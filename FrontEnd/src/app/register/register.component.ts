import { Component } from "@angular/core";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"],
})
export class RegisterComponent {
  //Khai báo các biến tương ứng trường dữ liệu trên form
  phone: string;
  password: string;
  retypePassword: string;
  fullName : string;
  address: string;
  isAccepted: boolean;

  constructor() {
    this.phone = "";
    this.password = "";
    this.retypePassword = "";
    this.fullName = "";
    this.address = "";
    this.isAccepted = false;
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`)
  }
}
