import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ExchangeOffers } from '../models/exchange-offers';

@Injectable({
  providedIn: 'root'
})
export class ExchangeoffersService {

  constructor(private http: HttpClient) { }

  getExchangeOffers() : Observable<ExchangeOffers[]>{
    return this.http.get<ExchangeOffers[]>('http://localhost:8080/api/exchangeOffers').pipe(map(res => res));
  }

  createExchangeOffer(response: any) : Observable<any>{
    return this.http.post<any>("http://localhost:8080/api/exchangeOffers/save", response).pipe(map(res => res));
  }
  
  deleteExchangeOffer(id: number) : Observable<any>{
    return this.http.delete<any>("http://localhost:8080/api/exchangeOffers/delete/" + id).pipe(map(res => res));
  }
}
