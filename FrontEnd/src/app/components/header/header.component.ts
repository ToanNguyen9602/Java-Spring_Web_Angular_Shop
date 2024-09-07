import { Component, OnInit } from "@angular/core";
import { NgbPopoverConfig } from "@ng-bootstrap/ng-bootstrap";
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

  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private popoverConfig: NgbPopoverConfig
  ) {}

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopooverOpen = !this.isPopooverOpen;
  }

  handleItemClick(index: number): void {
    alert(`${index}`)
    if (index === 2) {
      this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserResponseFromLocalStorage();
    }
    this.isPopooverOpen = false;
  }

  ngOnInit(): void {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
  }
}
