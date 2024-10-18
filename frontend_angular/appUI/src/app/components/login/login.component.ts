import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {

  credentials = {
    username: '',
    password: ''
  }

  constructor(private loginService: LoginService, private snackBar: MatSnackBar) { }

  onSubmit() {
    if (this.credentials.username && this.credentials.password) {
      //token generate
      this.loginService.generateToken(this.credentials).subscribe(
        (response: any) => {
          //if token successfully generated
          console.log(response.token);

          this.loginService.loginUser(response.token)
          //send to dashboard after login
          window.location.href = "/dashboard"
        },
        error => {
          //if token generation failed
          console.log(error);
          this.openSnackBar('Wrong password. Please try again.', 'Close');
        }
      )
    }
    else {
      console.log("Fields are empty");
      this.openSnackBar('Fields cannot be empty', 'Close');
    }
    console.log("login form submitted");
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 5000, // Duration in milliseconds
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
