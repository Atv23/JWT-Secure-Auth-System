import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, Validators } from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { merge } from 'rxjs';
import { SignupService } from '../../services/signup.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})

export class SignupComponent {
  user = {
    "name": '',
    "email": '',
    "password": '',
    "gender": '',
    "age": null as number | null,
    "image": null as File | null,
    "description": ''
  };
  readonly email = new FormControl('', [Validators.required, Validators.email]);
  errorMessage = signal('');

  constructor(private signupService: SignupService, private snackBar: MatSnackBar) {
    merge(this.email.statusChanges, this.email.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());
  }

  onSubmit() {
    // Prevent submission if any field is invalid
    if (!this.user.name || !this.user.email || !this.user.password || !this.user.gender || this.user.age === null) {
      console.error('All fields are required');
      return; // Exit the method if the form is invalid
    }

    const formData = new FormData();
    formData.append('name', this.user.name);
    formData.append('email', this.user.email);
    formData.append('password', this.user.password);
    formData.append('gender', this.user.gender);
    formData.append('age', this.user.age?.toString() || '');
    formData.append('description', this.user.description);

    if (this.user.image) {
      formData.append('image', this.user.image, this.user.image.name); // Add filename
    }

    // Use the service to send the data
    this.signupService.signup(formData).subscribe(
      response => {
        console.log('User signed up successfully', response);
        this.openSnackBar('Signup Successful!! Please Login', 'Close');
        window.location.href = "/login"
      },
      error => {
        console.error('Error during signup', error);
        this.openSnackBar('Something went wrong, please try again!', 'Close');
      }
    );
  }

  fileName: string = ''; 
  imagePreview: string | ArrayBuffer | null = '';
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.user.image = file;
      this.fileName = file.name; // Store the file name

      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result; // Store the image preview URL
      };
      reader.readAsDataURL(file); // Read the file as a Data URL for preview
    }
  }
  
  updateErrorMessage() {
    if (this.email.hasError('required')) {
      this.errorMessage.set('You must enter a value');
    } else if (this.email.hasError('email')) {
      this.errorMessage.set('Not a valid email');
    } else {
      this.errorMessage.set('');
    }
  }
  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 5000, // Duration in milliseconds
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
