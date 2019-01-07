import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreatePageComponent } from './create-page/create-page.component';
import { ServerLobbyComponent } from './server-lobby/server-lobby.component';
import { LandingPageComponent } from 'src/app/landing-page/landing-page.component';
import { LeaderboardPageComponent } from 'src/app/leaderboard-page/leaderboard-page.component';
import { WaitingPageComponent } from './waiting-page/waiting-page.component';
import { GamePageComponent } from './game-page/game-page.component';
import { SocketTestComponent } from './socket-test/socket-test.component';

// ROUTES: EXAMPLE
/*
const routes: Routes = [

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'employee', component: EmployeeComponent },
  { path: 'manager', component: ManagerComponent},
  { path: 'create', component: CreatePageComponent}

];
*/

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'create', component: CreatePageComponent },
  { path: 'leaderboard-page', component: LeaderboardPageComponent },
  { path: 'server-lobby', component: ServerLobbyComponent },
  { path: 'waiting', component: WaitingPageComponent },
  { path: 'game', component: GamePageComponent },
  { path: 'socket-test', component: SocketTestComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
