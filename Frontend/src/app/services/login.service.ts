import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Credentials } from '../models/credentials';
import { catchError, map} from 'rxjs/operators'
import { UtilsService } from './utils.service';
import { throwError } from 'rxjs';
import { UsersService } from './users.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private utilsService: UtilsService, private usersService: UsersService) { }

  // Check the credentials and if they are valid add the token and the user to LocalStorage
  login(creds: Credentials){
    return this.http.post("http://localhost:8080/login", creds, {
      observe: 'response'
    }).pipe(map((response: HttpResponse<any>) => {
      const body = response.body;
      const headers = response.headers;

      const bearerToken = headers.get('Authorization')!;
      const token = bearerToken.replace('Bearer ', '');

      localStorage.setItem('token', token);

      this.usersService.getUser(creds.username).subscribe(resp => {
        localStorage.setItem('user', this.utilsService.encrypt(resp));
      })

      return body;
    }),
    catchError((error: HttpErrorResponse) => {
      this.utilsService.showAlert("Invalid Credentials","Error");
      return throwError("Invalid Credentials");
    })
    );
  }
}
