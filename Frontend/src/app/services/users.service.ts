import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Users } from '../models/users';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { }

  getUser(username: string) : Observable<Users> {
    return this.http.get<Users>("http://localhost:8080/api/users/" + username).pipe(map(res => res));
  }

  createUser(response: any) : Observable<any>{
    return this.http.post<any>("http://localhost:8080/api/users/save", response).pipe(map(res => res));
  }

  updateUser(response: any) : Observable<any>{
    return this.http.put<any>("http://localhost:8080/api/users/update", response).pipe(map(res => res));
  }
}
