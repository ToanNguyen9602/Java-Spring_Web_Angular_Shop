import { Component, OnInit } from "@angular/core";
import { UserResponse } from "src/app/responses/user/user.response";
import { TokenService } from "src/app/service/token.service";
import { UserService } from "src/app/service/user.service";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"],
})
export class HeaderComponent implements OnInit {
  userResponse?: UserResponse | null;
  isPopooverOpen = false;

  constructor(private userService: UserService,
    private tokenService: TokenService
  ) {}

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopooverOpen = !this.isPopooverOpen;
  }
  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }
}
