import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {

  private category: String;
  private apiUrl: String = 'http://localhost:8080/TriviaTownesServer/';
  private socketUrl: String = 'ws://localhost:8080/TriviaTownesServer/';
  private lobbyKey;
  private username;
  private gameCategory;
  private lobbyName;
  private lobbyQuestions;
  private isLeader = false;
  private userId;
  private scope;

  constructor() { }

  public setScope(s: string): void {
    this.scope = s;
  }
  public getScope(): string {
    return this.scope;
  }

  public getSocketUrl(): String {
    return this.socketUrl;
  }

  public getUserId(): String {
    return this.userId;
  }
  public setUserId(userId: String): void {
    this.userId = userId;
  }

  public getGameCategory(): String {
    return this.gameCategory;
  }
  public setGameCategory(cat: String): void {
    this.gameCategory = cat;
  }

  public getLobbyName(): String {
    return this.lobbyName;
  }
  public setLobbyName(name: String): void {
    this.lobbyName = name;
  }

  public getLobbyQuestions(): String {
    return this.lobbyQuestions;
  }
  public setLobbyQuestions(number: String): void {
    this.lobbyQuestions = number;
  }

  public getIsLeader(): boolean {
    return this.isLeader;
  }

  public setIsLeader(status: boolean) {
    this.isLeader = status;
  }

  public getUsername(): String {
    return this.username;
  }

  public setUsername(username: String): void {
    this.username = username;
  }

  public getCategory(): String {
    return this.category;
  }

  public setCategory(category: String): void {
    this.category = category;
  }

  public getApiUrl(): String {
    return this.apiUrl;
  }

  public getLobbyKey(): String {
return this.lobbyKey;
  }

  public setLobbyKey(key: String): void {
    this.lobbyKey = key;
  }
}
