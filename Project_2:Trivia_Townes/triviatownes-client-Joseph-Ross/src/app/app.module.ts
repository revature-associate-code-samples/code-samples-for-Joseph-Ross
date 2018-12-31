import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DataTablesModule } from 'angular-datatables';
import { FormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import * as $ from 'jquery';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreatePageComponent } from './create-page/create-page.component';
import { RouterModule, Routes } from '@angular/router';

import { LandingPageComponent } from './landing-page/landing-page.component';
import { LeaderboardPageComponent } from './leaderboard-page/leaderboard-page.component';
import { ServerLobbyComponent } from './server-lobby/server-lobby.component';
import { WaitingPageComponent } from './waiting-page/waiting-page.component';
import { GamePageComponent } from './game-page/game-page.component';
import { SocketTestComponent } from './socket-test/socket-test.component';

import { StompConfig, StompService } from '@stomp/ng2-stompjs';

/*
const stompConfig: StompConfig = {
  // Which server?
  url: "ws://127.0.0.1:8080/WebSocketExample/chat",
  headers: {
    test: "Hello"
  },

  // How often to heartbeat?
  // Interval in milliseconds, set to 0 to disable
  heartbeat_in: 0, // Typical value 0 - disabled
  heartbeat_out: 20000, // Typical value 20000 - every 20 seconds

  // Wait in milliseconds before attempting auto reconnect
  // Set to 0 to disable
  // Typical value 5000 (5 seconds)
  reconnect_delay: 5000,

  // Will log diagnostics on console
  debug: true
};
*/

@NgModule({
  declarations: [
    AppComponent,
    CreatePageComponent,
    ServerLobbyComponent,
    LandingPageComponent,
    LeaderboardPageComponent,
    WaitingPageComponent,
    GamePageComponent,
    SocketTestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    DataTablesModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [

    /*
    StompService,
    {
      provide: StompConfig,
      useValue: stompConfig
    }
    */

    // {
    //   provide: InjectableRxStompConfig,
    //   useValue: myRxStompConfig
    // },
    // {
    //   provide: RxStompService,
    //   useFactory: rxStompServiceFactory,
    //   deps: [InjectableRxStompConfig]
    // }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
