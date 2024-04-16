import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Cards } from 'src/app/models/cards';
import { UserCards } from 'src/app/models/user-cards';
import { Users } from 'src/app/models/users';
import { CardsService } from 'src/app/services/cards.service';
import { UsercardsService } from 'src/app/services/usercards.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-pokedex',
  templateUrl: './pokedex.component.html',
  styleUrls: ['./pokedex.component.css']
})
export class PokedexComponent implements OnInit{

  cards: Cards[];
  userCards: UserCards[] = [];
  user: Users;
  amount = 0;

  constructor(private cardsService: CardsService, private userCardsService: UsercardsService,
    private utilsService: UtilsService, private router: Router
  ){}

  ngOnInit(): void {
    this.listAllCards();
    this.getUserCards();
  }

  getUserCards(){
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
    this.userCardsService.getUserCards(this.user.id).subscribe(resp =>{
      if(resp){
        this.userCards = resp;
      }
    });
  }

  listAllCards(){
    this.cardsService.getCards().subscribe(resp =>{
      if(resp){
        this.cards = resp;
      }
    });
  }

  checkUserHasCard(cardID: number) : boolean{
    const userCard = this.userCards.find(e => e.cards.id === cardID);
    if (userCard) {
      this.amount = userCard.amount;
      return true;
    } else {
      this.amount = 0;
      return false;
    }
  }

  showPokemonDetails(cardID: number){
    this.router.navigate(['/details', cardID]);
  }
}
