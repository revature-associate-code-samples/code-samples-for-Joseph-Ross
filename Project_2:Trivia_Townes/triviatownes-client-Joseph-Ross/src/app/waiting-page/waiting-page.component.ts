import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { StompService } from '@stomp/ng2-stompjs';
import { Message, StompHeaders } from '@stomp/stompjs';
import { Subscription, Observable } from 'rxjs';

import { GlobalsService } from '../globals.service';

@Component({
  selector: 'app-waiting-page',
  templateUrl: './waiting-page.component.html',
  styleUrls: ['./waiting-page.component.scss']
})
export class WaitingPageComponent implements OnInit, OnDestroy {

  // Stream of messages
  public data_subscription: Subscription;
  public data_observable: Observable<Message>;

  // Stream of chats
  public chat_subscription: Subscription;
  public chat_observable: Observable<Message>;

  public _stompService: StompService;

  public gameCategory;
  public gameNumberOfQuestion;
  public gameName;

  public disconnectOk = false;

  public userId;

  public gameKey;
  public scope;

  public chats = [];

  users: any;

  // Subscription status
  public subscribed = false;

  StompConfig = {
    url: this.globals.getSocketUrl() + 'join-waiting-lobby',
    headers: {

    },
    heartbeat_in: 0, // Typical value 0 - disabled
    heartbeat_out: 20000, // Typical value 20000 - every 20 seconds
    reconnect_delay: 0,
    debug: false // Will log diagnostics on console
  };

  connect() {
    this._stompService = new StompService(this.StompConfig);
    this._stompService.initAndConnect();

    this.data_observable = this._stompService.subscribe('/waiting/' + this.globals.getLobbyKey().toLowerCase() + '/' +
    this.userId + '/send-waiting');
    this.data_subscription = this.data_observable.subscribe(this.onUpdate);

    this.chat_observable = this._stompService.subscribe('/waiting/' + this.globals.getLobbyKey().toLowerCase() + '/send-chat');
    this.chat_subscription = this.chat_observable.subscribe(this.onChatUpdate);

    this.subscribed = true;

    this.startPingingServer(this);
  }

  public startPingingServer(self: any) {

    if(self.subscribed){
      self._stompService.publish('/waiting-update/' + self.globals.getLobbyKey() + '/' +
      self.userId +  '/update-waiting');
      setInterval(this.startPingingServer, 500, self);
    }
  }

  public onChatUpdate = (chat_observable: Message) => {

    const payload = JSON.parse(chat_observable.body);
    const message_user = payload['username'];
    const message_body = payload['message'];
    const userId = payload['userId'];
    const time = payload['time'];

    this.chats.push({
      username: message_user,
      message: message_body,
      id: userId,
      time: time
    });
  }

  public onUpdate = (data_observable: Message) => {

    const payload =  JSON.parse(data_observable.body);

    this.users = payload['players'];
    if (payload['status'] === 1) {
      this.unsubscribe();
      this.router.navigate(['game']);
    }
  }

  public sendMessage() {

    const username = this.globals.getUsername();
    const message = $('#message_input').val();
    $('#message_input').val('');

    this._stompService.publish('/waiting-update/' + this.globals.getLobbyKey() + '/recive-chat', JSON.stringify({
      username: username,
      message: message,
      userId: this.globals.getUserId()
    }));
  }

  public startGame(): void {

    const self = this;

    $.ajax({
      url: self.globals.getApiUrl() + 'start-game-connect',
      method: 'POST',
      data: {
        key: self.globals.getLobbyKey()
      },
      crossDomain: true,
      xhrFields: { withCredentials: true },
      success: function (res) {
        self.unsubscribe();
        self.disconnectOk = true;
        self.router.navigate(['game']);
      },
    });
  }

  public disconnect(): void {

    const self = this;

    if (!this.disconnectOk) {
      $.ajax({
        url: self.globals.getApiUrl() + 'disconnect',
        method: 'GET',
        crossDomain: true,
        xhrFields: { withCredentials: true }
      });
    }
  }


  constructor(
    public router: Router,
    public globals: GlobalsService
  ) { }

  ngOnInit() {
    if(!this.globals.getLobbyKey()) {
      this.router.navigate(['']);
      return;
    } else {
      this.gameCategory = this.globals.getGameCategory();
      this.gameName = this.globals.getLobbyName();
      this.gameNumberOfQuestion = this.globals.getLobbyQuestions();
      this.userId = this.globals.getUserId();
      this.gameKey = this.globals.getLobbyKey();

      if (this.globals.getScope() === 'true') {
        this.scope = 'private';
      } else {
        this.scope = 'public';
      }

      this.connect();
    }
  }

  ngOnDestroy(): void {
    this.unsubscribe();
  }

  public unsubscribe() {
    this.data_subscription = null;
    this.data_observable = null;
    this.chat_observable = null;
    this.chat_subscription = null;

    //this._stompService.disconnect();
    if(this.subscribed){
      this._stompService.deactivate();
      this.subscribed = false;
    }
    //this._stompService.
  }
}
