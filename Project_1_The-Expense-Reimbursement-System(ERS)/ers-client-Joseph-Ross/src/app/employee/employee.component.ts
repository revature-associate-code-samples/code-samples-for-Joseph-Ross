import { Component, OnInit } from '@angular/core';
import { Router, RouteReuseStrategy } from "@angular/router";
import { GlobalsService } from '../globals.service';
import { DataTableDirective } from 'angular-datatables';

import { AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements AfterViewInit, OnDestroy, OnInit {

  dollarAmount: number;
  description: String;
  type: String;

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings;

  dtTrigger: any = new Subject();

  constructor(
    public envVars: GlobalsService,
    public router: Router
  ){}

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy(): void {

    // Do not forget to unsubscribe the event
    this.dtTrigger.unsubscribe();
  }

  rerender(): void {
    
    console.log("before render");
    var self = this;

    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }

  ngOnInit() {
    //Redirect session if user is not logged in
    if(!this.envVars.isLoggedIn()){
      this.router.navigate(['login']);
    } else {
      //Loads all reimbursments
      this.loadSubmittedReimbursements();
    }
  }

  
  logout(): void {
    this.envVars.setUsername("");
    this.envVars.setPassword("");
    this.envVars.setFirstName("");
    this.envVars.setLastName("");
    this.envVars.setUserRole("");

    this.router.navigate(['login']);
  }

  loadSubmittedReimbursements(): void {

    //Used to refrence object scope inside ajax functions
    var self = this;

    this.dtOptions = {
      ajax: {
        url: this.envVars.getApiUrl() + "getEmployeeReimbursementRecords",
        data: {
          "username" : this.envVars.getUsername(),
          "password" : this.envVars.getPassword()
        },
        type: 'POST'
      },
      columns: [
        {
          title: 'Amount',
          data: 'amount'
        }, 
        {
          title: 'Submitted',
          data: 'timeSubmitted'
        }, 
        {
          title: 'Description',
          data: 'desc'
        },
        {
          title: 'Status',
          data: 'status'
        },
        {
          title: 'Type',
          data: 'type'
        },
      ]
    };
  }

  submitReimbursement(): void {

    console.log(this.type);

    var self = this;

    if(this.dollarAmount == null){
      alert("Please enter a dollar amount!");
      return;
    }

    if(this.dollarAmount <= 0){
      alert("Dollar Amount Must Be Positive!");
      return;
    }

    if(this.description.length == 0){
      alert("Please add a description!");
      return;
    }

    if(this.description.length > 250){
      alert("Description must be less that 251 characters!");
      return;
    }

    if(this.typeToIndex() == '0'){
      alert("Please select a valid type!");
      return;
    }

    $.ajax({
      url: this.envVars.getApiUrl() + "submitReimbursementRequest",
      data: {
        "username" : this.envVars.getUsername(),
        "password" : this.envVars.getPassword(),
        "amount" : this.dollarAmount,
        "descr" : this.description,
        "type" : this.typeToIndex()
      },
      type: 'POST',
      success: function (response: String) {

        self.type = undefined;
        self.dollarAmount = 0;
        self.description = "";

        self.rerender();
        
      }
    });

  }

  typeToIndex(): String {
    if(this.type == "lodging") return '1';
    if(this.type == "travel") return '2';
    if(this.type == "food") return '3';
    if(this.type == "other") return '4';
    return '0';
  }

}
