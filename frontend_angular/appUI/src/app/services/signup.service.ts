import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) {}

  signup(userData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, userData);
  }
}
