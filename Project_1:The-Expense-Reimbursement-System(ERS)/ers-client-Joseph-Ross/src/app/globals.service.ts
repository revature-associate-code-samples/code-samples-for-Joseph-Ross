import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {

  private apiName: String = "http://localhost:8080/bookstore-full-stack/";
  private username: String;
  private password: String;
  private firstname: String;
  private lastname: String;
  private userrole: Number;

  constructor() {}

  public isLoggedIn(): Boolean {
    //The username and password will only be set if server approved credentials
    if(this.username == null || this.password == null) return false;
    return true;
  }
  public getApiUrl(): String {
    return this.apiName;
  }
  public getUsername(): String {
    return this.username;
  }
  public setUsername(username): void {
    this.username = username;
  }
  public getPassword(): String {
    return this.password;
  }
  public setPassword(password): void {
    this.password = password;
  }
  public getFirstName(): String {
    return this.firstname;
  }
  public setFirstName(firstname): void {
    this.firstname = firstname;
  }
  public getLastName(): String {
    return this.lastname;
  }
  public setLastName(lastname): void {
    this.lastname = lastname;
  }
  public getUserRole(): Number {
    return this.userrole;
  }
  public setUserRole(userrole): void {
    this.userrole = userrole;
  }
}
