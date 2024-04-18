import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { UserCards } from 'src/app/models/user-cards';
import { Users } from 'src/app/models/users';
import { UsercardsService } from 'src/app/services/usercards.service';
import { UtilsService } from 'src/app/services/utils.service';
import * as bootstrap from 'bootstrap';
import { Cards } from 'src/app/models/cards';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  user: Users;
  userCards: UserCards;
  card: Cards;

  constructor(private utilsService: UtilsService, private router: Router,
    private userCardsService: UsercardsService
  ){}

  ngOnInit(): void {
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  buyGift(idGift: number){
    this.userCardsService.postRandomCard(idGift, this.user).subscribe(resp =>{
      this.userCards = resp;
      localStorage.setItem('user', this.utilsService.encrypt(this.userCards.users));
      this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
      this.card = this.userCards.cards;
      var modal = document.getElementById('giftModal');

      var modalInstance = new bootstrap.Modal(modal);

      modalInstance.show();
    },
    (error: HttpErrorResponse) => {
      this.utilsService.showAlert(error.error.error,"Error");
      return throwError(error.error.error);
    })
  }
}