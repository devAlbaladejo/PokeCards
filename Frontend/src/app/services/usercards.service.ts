import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { UserCards } from '../models/user-cards';

@Injectable({
  providedIn: 'root'
})
export class UsercardsService {

  constructor(private http: HttpClient) { }

  getUserCards(id: number) : Observable<UserCards[]>{
    return this.http.get<UserCards[]>('http://localhost:8080/api/userCards/' + id).pipe(map(res => res));
  }

  postRandomCard(giftID: number, userCard: any) : Observable<UserCards>{
    return this.http.post<UserCards>('http://localhost:8080/api/userCards/save/' + giftID, userCard).pipe(map(res => res));
  }
}
