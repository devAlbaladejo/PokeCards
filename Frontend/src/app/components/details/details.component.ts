import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Cards } from 'src/app/models/cards';
import { UserCards } from 'src/app/models/user-cards';
import { Users } from 'src/app/models/users';
import { CardsService } from 'src/app/services/cards.service';
import { UsercardsService } from 'src/app/services/usercards.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  card: Cards;
  userCards: UserCards[] = [];
  user: Users;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cardsService: CardsService,
    private userCardsService: UsercardsService,
    private utilsService: UtilsService,
    private titleService: Title
  ) {}

  // Method that's executed when the component is loaded
  ngOnInit(): void {
    this.titleService.setTitle('Pokemon Details');
    
    // If the user does not has the card, we return it to the pokedex
    this.route.params.pipe(
      switchMap(params => {
        const cardId = +params['id'];
        return this.checkUserHasCard(cardId);
      })
    ).subscribe(hasCard => {
      if (hasCard) {
        const cardId = +this.route.snapshot.params['id'];
        this.list(cardId);
      } else {
        this.router.navigate(['/pokedex']);
      }
    });
  }

  list(cardId: number): void {
    this.cardsService.getCard(cardId).subscribe(resp => {
      if (resp) {
        this.card = resp;
        this.progress();
      }
    });
  }

  // Check if user has the card
  checkUserHasCard(cardID: number): Observable<boolean> {
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);
    return this.userCardsService.getUserCards(this.user.id).pipe(
      switchMap(userCards => {
        if (userCards) {
          this.userCards = userCards;
          return of(!!this.userCards.find(e => e.cards.id === cardID));
        } else {
          return of(false);
        }
      })
    );
  }

  // Paint progress bars with pokemon primary type
  progress(){
    const rgbaColor = this.rgbaFromHex(this.card.primaryType.color);
    const style = document.createElement("style");
    style.innerHTML = `
    .stat-container .stat-progress::-webkit-progress-bar {
        background-color: rgba(${rgbaColor}, 0.5);
        border-radius: 5px;
    }
    .stat-container .stat-progress::-webkit-progress-value {
        background-color: ${this.card.primaryType.color};
        border-radius: 5px;
    }
  `;
  document.head.appendChild(style);
  }

  // Convert color hexadecimal to rgba
  rgbaFromHex(hexColor: string) {
    return [
      parseInt(hexColor.slice(1, 3), 16),
      parseInt(hexColor.slice(3, 5), 16),
      parseInt(hexColor.slice(5, 7), 16),
    ].join(", ");
  }
}
