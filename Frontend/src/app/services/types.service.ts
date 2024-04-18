import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Types } from '../models/types';

@Injectable({
  providedIn: 'root'
})
export class TypesService {

  constructor(private http: HttpClient) { }

  getTypes() : Observable<Types[]>{
    return this.http.get<Types[]>('http://localhost:8080/api/types').pipe(map(res => res));
  }
}
