import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Users } from 'src/app/models/users';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  user: Users;

  constructor(private utilsService: UtilsService, private router: Router){}

  ngOnInit(): void {

    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}