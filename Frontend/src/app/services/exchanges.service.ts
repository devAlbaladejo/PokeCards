import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Exchanges } from '../models/exchanges';

@Injectable({
  providedIn: 'root'
})
export class ExchangesService {

  constructor(private http: HttpClient) { }

  getExchanges() : Observable<Exchanges[]>{
    return this.http.get<Exchanges[]>("http://localhost:8080/api/exchanges").pipe(map(res => res));
  }

  createExchange(response: any) : Observable<any>{
    return this.http.post<any>("http://localhost:8080/api/exchanges/change", response).pipe(map(res => res));
  }
}
