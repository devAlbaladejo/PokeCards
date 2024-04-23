import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { Users } from 'src/app/models/users';
import { UsersService } from 'src/app/services/users.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user: Users = Object.create(null);
  formRegister: FormGroup;

  constructor(private utilsService: UtilsService, private usersService: UsersService,
    private router: Router, private titleService: Title
  ){}

  ngOnInit(): void {

    this.titleService.setTitle('Register');

    this.formRegister = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]),
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required)
    })
  }

  register(){
    this.setUserData();

    if(this.formRegister.value.password != this.formRegister.value.confirmPassword)
      this.utilsService.showAlert('Passwords don\'t match','Error');
    else{
      this.usersService.createUser(this.user).subscribe(resp => {
        this.utilsService.showAlert('User created','');
        this.router.navigate(['/login']);
      },
      (error: HttpErrorResponse) => {
        this.utilsService.showAlert(error.error.error,"Error");
        return throwError(error.error.error);
      })
    }
  }

  setUserData(){
    this.user.id = 0;
    this.user.username = this.formRegister.value.username;
    this.user.email = this.formRegister.value.email;
    this.user.password = this.formRegister.value.password;
    this.user.points = 0;
  }
}
