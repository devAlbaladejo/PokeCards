import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from 'src/app/models/credentials';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  creds: Credentials = {
    username: '',
    password: ''
  }

  constructor(private loginService: LoginService, private router: Router){}

  login() {
    this.loginService.login(this.creds).subscribe(
      () => {
        setTimeout(() => {
          this.router.navigate(['/home']);
        }, 500);
      });
  }
}
