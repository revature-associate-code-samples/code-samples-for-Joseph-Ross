import { Component, OnInit } from '@angular/core';
import { Router, RouteReuseStrategy } from "@angular/router";
import { THROW_IF_NOT_FOUND } from '@angular/core/src/di/injector';
import { routerNgProbeToken } from '@angular/router/src/router_module';

import { GlobalsService } from '../globals.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit {

  public username: String;
  public password: String;

  constructor(
                public envVars: GlobalsService,
                public router: Router
              ){
              }

  ngOnInit() {
    //Redirect session if user is already logged in
    this.checkSession();
  }

  //Used to check if the user has already logged in
  //If so redirect them to the correct page
  checkSession(): void {
    if(this.envVars.isLoggedIn)
      this.navigateUser();
  }

  //Sends users to a page depending on the role of the user
  navigateUser(): void {
    if(this.envVars.getUserRole() == 1)
      this.router.navigate(['employee']);

    if(this.envVars.getUserRole() == 2)
      this.router.navigate(['manager']);
  }

  //Will send request to server and verify 
  attemptLogin(): void {

    //Used to refrence object scope inside ajax functions
    var self = this;
    
    $.ajax({
        url: this.envVars.getApiUrl() + "login",
        data: {
          "username" : this.username,
          "password" : this.password
        },
        type: 'POST',
        success: function (response: String) {

          //These vars will be used to trace the virtual session
          self.envVars.setUsername(self.username);
          self.envVars.setPassword(self.password);
          self.envVars.setFirstName(response['firstName']);
          self.envVars.setLastName(response['lastName']);
          self.envVars.setUserRole(response['role']);

          //Send the user to correct page
          self.navigateUser();
        }
    });
  }
}
