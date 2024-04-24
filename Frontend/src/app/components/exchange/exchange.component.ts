import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { Cards } from 'src/app/models/cards';
import { ExchangeOffers } from 'src/app/models/exchange-offers';
import { Exchanges } from 'src/app/models/exchanges';
import { UserCards } from 'src/app/models/user-cards';
import { Users } from 'src/app/models/users';
import { CardsService } from 'src/app/services/cards.service';
import { ExchangeoffersService } from 'src/app/services/exchangeoffers.service';
import { ExchangesService } from 'src/app/services/exchanges.service';
import { UsercardsService } from 'src/app/services/usercards.service';
import { UtilsService } from 'src/app/services/utils.service';
import * as bootstrap from 'bootstrap';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-exchange',
  templateUrl: './exchange.component.html',
  styleUrls: ['./exchange.component.css']
})
export class ExchangeComponent implements OnInit{

  exchangeOffers: ExchangeOffers[];
  exchangeOffer: ExchangeOffers = new ExchangeOffers();
  userCards: UserCards[];
  filterUserCards: UserCards[];
  allCards: Cards[];
  filterAllCards: Cards[];
  cardOffer?: Cards;
  cardsDesire: Cards[] = [];
  cardDemand?: Cards;
  exchanges: Exchanges[];
  exchange: Exchanges = new Exchanges();
  user: Users;
  amount = 0;
  removing = false;

  constructor(private exchangeOffersService: ExchangeoffersService, private userCardsService: UsercardsService,
    private utilsService: UtilsService, private cardsService: CardsService, private exchangesService: ExchangesService,
    private titleService: Title
  ){}

  ngOnInit(): void {
    this.titleService.setTitle('Exchange');
    this.loadExchanges();
    this.listUserCards();
    this.listCards();
  }

  loadExchanges(){
    this.listExchangeOffers();
    this.listExchanges();
  }

  listExchangeOffers(){
    this.exchangeOffersService.getExchangeOffers().subscribe(resp => {
      if(resp){
        this.exchangeOffers = resp;
        this.exchangeOffers = this.exchangeOffers.filter(e => e.active == 1);
      }
    })
  }

  listUserCards(){
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
    this.userCardsService.getUserCards(this.user.id).subscribe(resp => {
      if(resp){
        this.userCards = resp;
        this.filterUserCards = resp; 
      }
    })
  }

  listCards(){
    this.cardsService.getCards().subscribe(resp => {
      if(resp){
        this.allCards = resp;
        this.filterAllCards = resp;
      }
    });
  }

  listExchanges(){
    this.exchangesService.getExchanges().subscribe(resp => {
      if(resp){
        this.exchanges = resp;
      }
    });
  }

  newExchangeOffer(){
    if(this.userCards.length == 0)
      this.showMessage('You don\'t have cards','Error');
    else{
      this.enableCards('desire-list');
      this.utilsService.openModal('exchangeOfferModal');
    }
  }

  addExchangeOffer(){
    this.exchangeOffer.userOffer = this.user;
    this.exchangeOffer.cardOffer = this.cardOffer!;
    this.exchangeOffer.desiredCard1 = this.cardsDesire[0];
    this.exchangeOffer.desiredCard2 = this.cardsDesire[1]
    this.exchangeOffer.desiredCard3 = this.cardsDesire[2];
    this.exchangeOffer.active = 1;

    this.exchangeOffersService.createExchangeOffer(this.exchangeOffer).subscribe(resp => {
      if(resp){
        this.loadExchanges();
        this.showMessage('Exchange offer created');
      }
    });
  }

  deletedExchangeOffer(exchangeOffers: ExchangeOffers){
    this.exchangeOffer = exchangeOffers;
    this.exchangeOffersService.deleteExchangeOffer(this.exchangeOffer.id).subscribe(resp => {
      if(resp){
        this.showMessage('Exchange offer deleted');
        this.loadExchanges();
      }
    })
  }

  // shows the number of cards you have 
  checkAmount(cardID: number) : string{
    const userCard = this.userCards.find(e => e.cards.id === cardID);
    if (userCard){
      this.amount = userCard.amount;
      return '';
    }
    else {
      this.amount = 0;
      return 'disabled';
    }
  }

  addCardOffer(event: MouseEvent, card: Cards){
    if(this.cardOffer == undefined && !this.removing){
      this.cardOffer = card;
      const targetElement = event.currentTarget as HTMLElement;
      targetElement.classList.add('selected')
      this.disableCards('offer-list');
    }

    if(this.removing)
      this.removing = false;
  }

  addCardsDesire(event: MouseEvent, card: Cards){

    const targetElement = event.currentTarget as HTMLElement;

    if((this.cardsDesire == undefined || this.cardsDesire.length < 3) && !this.removing ){
      this.cardsDesire.push(card);
      targetElement.classList.add('selected')

      if(this.cardsDesire.length == 3)
        this.disableCards('desire-list');
    } 

    if(this.removing){
      targetElement.classList.remove('selected')
      this.removing = false;
    }
      
  }

  addCardDemand(event: MouseEvent, card: Cards){
    if(this.cardDemand == undefined && !this.removing){

      const targetElement = event.currentTarget as HTMLElement;
 
      if(!targetElement.classList.contains('disabled')){
        this.cardDemand = card;
        targetElement.classList.add('selected');
        this.disableCards('demand-list');
      }
    }

    if(this.removing)
      this.removing = false;
  }

  enableCards(divID: string, activeCards: Cards[] = []){
    let div = document.getElementById(divID);
    let cards = div?.getElementsByClassName('pokemon-card');

    for (let i = 0; i < cards!.length; i++) {

      if(cards![i].classList.contains('selected') && activeCards.length == 0)
        cards![i].classList.remove('selected');

      if(divID != 'demand-list')
        cards![i].classList.remove('disabled');
      else{
        let divRemove = cards![i].getElementsByClassName('pokemon-rarity')[0].getElementsByClassName('pokemon-remove');

        if(divRemove.length != 0){
          cards![i].classList.remove('disabled');
        }
      }
    }
  }

  disableCards(divID: string){
    let div = document.getElementById(divID);
    let cards = div?.getElementsByClassName('pokemon-card');

    for (let i = 0; i < cards!.length; i++) {
      if(!cards![i].classList.contains('selected'))
        cards![i].classList.add('disabled');
    }
  }

  removeCardOffer(){
    this.enableCards('offer-list');
    this.cardOffer = undefined;
    this.removing = true;
  }

  removeCardDesire(cardID: number){
    this.cardsDesire = this.cardsDesire.filter(card => card.id !== cardID);
    this.enableCards('desire-list', this.cardsDesire);
    this.removing = true;
  }

  removeCardDemand(){
    this.enableCards('demand-list');
    this.cardDemand = undefined;
    this.removing = true;
  }

  checkUserHasDemandCards(exchangeOffer: ExchangeOffers) : boolean{
    return this.userCards.some(e => {
      return e.cards.id === exchangeOffer.desiredCard1.id 
          || (exchangeOffer.desiredCard2?.id && e.cards.id === exchangeOffer.desiredCard2.id)
          || (exchangeOffer.desiredCard3?.id && e.cards.id === exchangeOffer.desiredCard3.id);
    });
  }

  getDataExchange(exchangeOffers: ExchangeOffers){
    if(this.checkUserHasDemandCards(exchangeOffers)){
      this.exchange.exchangeOffer = exchangeOffers;
      this.utilsService.openModal('exchangeModal');
    }
    else
      this.showMessage('You don\'t have any cards that the user wants','Error');
  }

  change(){
    this.exchange.userDemand = this.user;
    this.exchange.cardDemand = this.cardDemand!;

    this.exchangesService.createExchange(this.exchange).subscribe(resp => {
      if(resp){
        this.showMessage('Exchange has been made');
        this.loadExchanges();
      }
    },
    (error: HttpErrorResponse) => {
      console.log(error.error.error);
      this.showMessage(error.error.error,"Error");
      return throwError(error.error.error);
    })
  }

  filterOfferPokemons(event: KeyboardEvent) {
    const searchTerm = (event.target as HTMLInputElement).value;

    if (!searchTerm.trim()) {
      // If value is empty, show pokemons that user has
      this.filterUserCards = this.userCards;
    } else {
      // Filter pokemon for search value
      this.filterUserCards = this.userCards.filter(card =>
        card.cards.name.toLowerCase().includes(searchTerm.toLowerCase().trim())
      );
    }
  }

  filterDesirePokemons(event: KeyboardEvent) {
    const searchTerm = (event.target as HTMLInputElement).value;

    if (!searchTerm.trim()) {
      // If value is empty, show all pokemons
      this.filterAllCards = this.allCards;
    } else {
      // Filter pokemon for search value
      this.filterAllCards = this.allCards.filter(card =>
        card.name.toLowerCase().includes(searchTerm.toLowerCase().trim())
      );
    }
  }

  // check if the desired card is selected
  isSelectedCard(card: any): boolean {
    return this.cardsDesire.some(e => e.id == card.id);
  }

  // Set class if card is available or not
  setClass(card: any) {
    const isSelected = this.isSelectedCard(card);
    return {
        'selected': isSelected,
        'disabled': !isSelected && this.cardsDesire.length === 3
    };
  }

  showMessage(message: string, action: string = ''){
    setTimeout(() => {
      this.utilsService.showAlert(message, action);
    }, 300);
  }
}
