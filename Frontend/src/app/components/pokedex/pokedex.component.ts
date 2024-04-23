import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
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

  allCards: Cards[];
  cards: Cards[];
  userCards: UserCards[] = [];
  user: Users;
  amount = 0;

  constructor(private cardsService: CardsService, private userCardsService: UsercardsService,
    private utilsService: UtilsService, private router: Router, private titleService: Title
  ){}

  // Method that's executed when the component is loaded
  ngOnInit(): void {
    this.titleService.setTitle('Pokedex');
    this.listAllCards();
    this.listUserCards();
  }

  // Lists all the cards the user has
  listUserCards(){
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
    this.userCardsService.getUserCards(this.user.id).subscribe(resp =>{
      if(resp){
        this.userCards = resp;
      }
    });
  }

  // List all pokemons
  listAllCards(){
    this.cardsService.getCards().subscribe(resp =>{
      if(resp){
        this.allCards = resp;
        this.cards = resp;
      }
    });
  }

  // Checks that the user has the letter passed by parameter. 
  // If it does not have it, it is shown as blocked
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

  filterPokemons(event: KeyboardEvent) {
    const searchTerm = (event.target as HTMLInputElement).value;
    const clearSearch = document.getElementsByClassName('search-close')[0];

    if (!searchTerm.trim()) {
      // If value is empty, show all pokemons
      this.listAllCards();
      clearSearch.classList.remove('search-close-visible');
    } else {
      // Filter pokemon for search value
      this.cards = this.allCards.filter(card =>
        card.name.toLowerCase().includes(searchTerm.toLowerCase().trim()) && this.userCards.some(e => e.cards.id == card.id)
      );

      clearSearch.classList.add('search-close-visible');
    }
  }

  // Clear search value and show all pokemons
  clearSearch(){
    let input = document.getElementById('search-input');
    (input as HTMLInputElement).value = '';
    this.listAllCards();
  }
}
