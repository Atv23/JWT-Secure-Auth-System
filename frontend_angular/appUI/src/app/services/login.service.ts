import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl:string="http://localhost:8081";

  constructor(private http:HttpClient){

  }
  //calling server to get token
generateToken(credentials:any){
  //token generate
  return this.http.post(`${this.baseUrl}/token`,credentials)
}

  //for login user
  loginUser(token:any)
  {
      localStorage.setItem("token", token)
      return true;
  }
  //to check if user is logged in
  isLoggedIn()
  {
    let token = localStorage.getItem("token");
    if(token === undefined || token==='' || token===null)
    {
      return false;
    }
    else{
      return true;
    }
  }
  //log out user
  logout()
  {
    localStorage.removeItem('token')
    return true;
  }
  //for getting token
  getToken()
  {
    return localStorage.getItem('token');
  }
}
