import { Component, inject } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  public isLoggedIn = false;
  public userName: string | undefined; // Store the user name

  constructor(private loginService: LoginService, private userService: UserService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn()
    console.log("Login Status: ", this.isLoggedIn);
    if (this.isLoggedIn) {
      this.getUserDetails(); // Fetch user details if logged in
    }
  }
  getUserDetails() {
    this.userService.getUser().subscribe({
      next: (response: any) => {
        this.userName = response.name; // Adjust according to the actual response structure
      },
      error: (err) => {
        console.error('Error fetching user details', err);
      }
    });
  }
  logoutUser()
  {
    this.loginService.logout()
    location.reload()
  }
}
