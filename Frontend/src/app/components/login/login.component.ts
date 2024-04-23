import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Credentials } from 'src/app/models/credentials';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  creds: Credentials = {
    username: '',
    password: ''
  }

  constructor(private loginService: LoginService, private router: Router,
    private titleService: Title
  ){}

  ngOnInit(): void {
    this.titleService.setTitle('Login');
  }

  login() {
    this.loginService.login(this.creds).subscribe(
      () => {
        setTimeout(() => {
          this.router.navigate(['/home']);
        }, 500);
      });
  }
}
