import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Types } from 'src/app/models/types';
import { TypesService } from 'src/app/services/types.service';
import { UtilsService } from 'src/app/services/utils.service';
import { UsersService } from 'src/app/services/users.service';
import { Users } from 'src/app/models/users';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-combat',
  templateUrl: './combat.component.html',
  styleUrls: ['./combat.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CombatComponent implements OnInit{

  user: Users;
  userPoints: number = 0;
  cpuPoints: number = 0;
  userType: Types;
  cpuType: Types;
  types: Types[];
  typesAndStrongID: Map<Types, number[]> = new Map(); 

  constructor(private typesService: TypesService, private utilsService: UtilsService,
    private usersService: UsersService, private router: Router, private titleService: Title
  ) {  }

  ngOnInit(): void {
    this.titleService.setTitle('Combat');
    this.utilsService.openModal('infoModal')
    this.listTypes();
  }

  listTypes(){
    this.typesService.getTypes().subscribe(resp => {
      if(resp){
        this.types = resp;
        this.addTypesAndStrong();
      }
    })
  }

  // Adds all types to which each type is strong
  addTypesAndStrong(){
    this.types.forEach(e => {
      if (e && e.strong) { 
          let strongs: number[] = e.strong.split(';').map(strong => Number(strong.trim()));
          this.typesAndStrongID.set(e, strongs);
      } else {
          this.typesAndStrongID.set(e, []);
      }
    });
  }

  // Start game
  play(){
    this.userPoints = 0;
    this.cpuPoints = 0;

    this.utilsService.closeModal('infoModal');

    this.showTypes();
  }

  showTypes(){
    setTimeout(() => {
      this.utilsService.openModal('typesModal');
    }, 1000);
  }

  userSelectedType(type: Types){
    this.selectType('user', type);
  }

  cpuSelectType(){
    const number = this.randomNumber(this.types.length);
    const type = this.types[number - 1];
    this.selectType('cpu', type);
  }

  randomNumber(max: number) : number{
    return Math.floor(Math.random() * max) + 1;
  }

  selectType(player: string, type: Types){
    if (player === 'user') {
      this.userType = type;
    } else if (player === 'cpu') {
      this.cpuType = type;
    }
  
    this.createCard(player, type);
  
    if (player === 'user') {
      setTimeout(() => {
        this.cpuSelectType();
      }, 1000);
    } else {
      this.checkSelections();
    }
  }

  createCard(player: string, type: Types){
    let div = document.getElementById(player + '-card');
    let card = document.createElement('div');
    let text = document.createElement('p');
    card.setAttribute('class', 'card');
    card.style.backgroundColor = type.color;
    text.innerText = type.name;
    card.appendChild(text);
    div?.appendChild(card);
  }

  removeCards(){
    let userCard = document.getElementById('user-card')?.getElementsByClassName('card')[0];
    let cpuCard = document.getElementById('cpu-card')?.getElementsByClassName('card')[0];

    userCard?.remove();
    cpuCard?.remove();
  }

  // Check if user or CPU get a point
  checkSelections(){
    let userScore = false;
    let cpuScore = false;

    const userStrongs = this.typesAndStrongID.get(this.userType);
    const cpuStrongs = this.typesAndStrongID.get(this.cpuType);

    if(userStrongs?.includes(this.cpuType.id))
      userScore = true;

    if(cpuStrongs?.includes(this.userType.id))
      cpuScore = true;
    
    if(userScore && !cpuScore){
      this.utilsService.showAlert('Point for user','');
      this.userPoints++;
    }
    else if(!userScore && cpuScore){
      this.utilsService.showAlert('Point for cpu','');
      this.cpuPoints++;
    }
    else if(userScore && cpuScore){
      this.utilsService.showAlert('Point for both','');
    }
    else
      this.utilsService.showAlert('Nobody scores','');

    this.checkSomeoneWin();
  }

  checkSomeoneWin(){
    setTimeout(() => {

      this.removeCards();

      if(this.userPoints == 1 && this.cpuPoints == 0){
        this.utilsService.showAlert('User Win! You have earned 10 points','');
        this.addUserPoints();
        setTimeout(() => {
          this.showEndGame();
        }, 3000);
      }
      else if(this.cpuPoints == 1 && this.userPoints == 0){
        this.utilsService.showAlert('CPU Win!','');
        setTimeout(() => {
          this.showEndGame();
        }, 3000);
      }
      else if(this.userPoints == 1 && this.cpuPoints == 1){
        this.utilsService.showAlert('Draw! Nobody wins','');
        setTimeout(() => {
          this.showEndGame();
        }, 3000);
      }
      else
        this.showTypes();
    }, 3000);
  }

  addUserPoints(){
    this.user = this.utilsService.decryptData(localStorage.getItem('user')!);

    this.usersService.updateUser(this.user).subscribe(resp => {
      if(resp){
        localStorage.setItem('user', this.utilsService.encrypt(resp.users));
      }
    });
  }

  showEndGame(){
    this.utilsService.openModal('endModal');
  }

  home(){
    this.router.navigate(['/home']);
  }
}
