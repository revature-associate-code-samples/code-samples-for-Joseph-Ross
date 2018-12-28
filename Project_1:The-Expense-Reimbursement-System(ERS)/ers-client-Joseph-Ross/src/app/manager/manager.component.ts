import { Component, OnInit } from '@angular/core';
import { Router, RouteReuseStrategy } from "@angular/router";
import { GlobalsService } from '../globals.service';
import { DataTableDirective } from 'angular-datatables';

import { AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements AfterViewInit, OnDestroy, OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: DataTables.Settings;

  dtTrigger: any = new Subject();

  public selectedReimbId: String;
  public selectedReimbDescision: String;

  constructor(
    public envVars: GlobalsService,
    public router: Router
  ) {}

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

    console.log("after render");
    
  }

  ngOnInit() {

    //Redirect session if user is not logged in
    if(!this.envVars.isLoggedIn()){
      this.router.navigate(['login']);
      
    } else {

      //Loads all reimbursments
      this.loadAllReimbursements();
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

  loadAllReimbursements(): void {

    var self = this;

    this.dtOptions = {
      ajax: {
        url: this.envVars.getApiUrl() + "getAllReimbursementsServlet",
        data: {
          "username" : this.envVars.getUsername(),
          "password" : this.envVars.getPassword()
        },
        type: 'POST',
        complete: function(settings, json) {

          $('#reimb-table tbody').unbind();
          $('#reimb-table').unbind();
      
          var table = $('#reimb-table').DataTable();
        
          $('#reimb-table tbody').on( 'click', 'tr', function () {
      
              $('#approve-btn').prop( "disabled", true );
              $('#deny-btn').prop( "disabled", true );
              $('#reimb-id-display').text("-");
      
              table.$('tr.selected').removeClass('selected');
              $(this).addClass('selected');


      
              if($(this).children().eq(5).text() == "PENDING"){
                self.selectedReimbId = $(this).children().eq(0).text();
      
                $('#approve-btn').prop( "disabled", false );
                $('#deny-btn').prop( "disabled", false );
      
                $('#reimb-id-display').text(self.selectedReimbId + "");
              }
          });
        }
      },
      columns: [
        {
          title: 'Id',
          data: 'reimbId'
        },
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
          title: 'Username',
          data: 'username'
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

  approve(): void {
    this.submitReimbursmentDescision(2);
  }

  deny(): void {
    this.submitReimbursmentDescision(3);
  }

  submitReimbursmentDescision(decision: number): void {
    var self = this;
    $.ajax({
      url: this.envVars.getApiUrl() + "managerReimbursementDecision",
      data: {
        "username" : this.envVars.getUsername(),
        "password" : this.envVars.getPassword(),
        "reimbId" : this.selectedReimbId,
        "decision" : decision
      },
      type: 'POST',
      success: function (response: String) {
        self.rerender();

        $('#approve-btn').prop( "disabled", true );
        $('#deny-btn').prop( "disabled", true );
        $('#reimb-id-display').text("-");
      },
      
    });
  }
}
