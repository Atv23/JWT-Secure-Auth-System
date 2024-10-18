import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  public isLoggedIn = false;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn()
    console.log("Login Status: ", this.isLoggedIn);
  }

  logoutUser()
  {
    this.loginService.logout()
    location.reload()
  }
}
