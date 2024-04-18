import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Types } from 'src/app/models/types';
import { TypesService } from 'src/app/services/types.service';
import * as bootstrap from 'bootstrap';
import { UtilsService } from 'src/app/services/utils.service';
import { UsersService } from 'src/app/services/users.service';
import { Users } from 'src/app/models/users';
import { Router } from '@angular/router';

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
    private usersService: UsersService, private router: Router
  ) {  }

  ngOnInit(): void {
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

  play(){

    this.userPoints = 0;
    this.cpuPoints = 0;

    let divEnd = document.getElementById('end-screen');
    if (divEnd) {
      divEnd.style.zIndex = '-2';
    }

    let divInfo = document.getElementById('info-screen');
    if (divInfo) {
      divInfo.style.zIndex = '-1';
      this.showModal();
    }
  }

  showModal(){
    setTimeout(() => {
      var modal = document.getElementById('typesModal');

      var modalInstance = new bootstrap.Modal(modal);

      modalInstance.show();
    }, 1000);
  }

  userSelectedType(type: Types){
    this.userType = type;
    this.createCard('user', this.userType);

    setTimeout(() => {
      this.cpuSelectType();
    }, 1000);
  }

  cpuSelectType(){
    let number = this.randomNumber(this.types.length);
    this.cpuType = this.types[number - 1];
    this.createCard('cpu', this.cpuType);
    this.checkSelections();
  }

  randomNumber(max: number) : number{
    return Math.floor(Math.random() * max) + 1;
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

      if(this.userPoints == 3){
        this.utilsService.showAlert('User Win!','');
        this.addUserPoints();
        this.showEndScreen();
      }
      else if(this.cpuPoints == 3){
        this.utilsService.showAlert('CPU Win!','');
        this.showEndScreen();
      }
      else
        this.showModal();
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

  showEndScreen(){
    let divEnd = document.getElementById('end-screen');
    if (divEnd) {
      divEnd.style.zIndex = '2';
    }
  }

  home(){
    this.router.navigate(['/home']);
  }
}
