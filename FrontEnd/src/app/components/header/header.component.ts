import { Component, OnInit } from '@angular/core';
import { UserResponse } from 'src/app/responses/user/user.response';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  userResponse?: UserResponse | null
  isPopooverOpen = false;

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

}
