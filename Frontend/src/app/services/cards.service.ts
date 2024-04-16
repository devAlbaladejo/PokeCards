import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Cards } from '../models/cards';

@Injectable({
  providedIn: 'root'
})
export class CardsService {

  constructor(private http: HttpClient) { }

  getCards() : Observable<Cards[]>{
    return this.http.get<Cards[]>('http://localhost:8080/api/cards').pipe(map(res => res));
  }

  getCard(id: number) : Observable<Cards>{
    return this.http.get<Cards>('http://localhost:8080/api/cards/' + id).pipe(map(res => res));
  }
}
