import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './helpers/auth.guard';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { PokedexComponent } from './components/pokedex/pokedex.component';
import { DetailsComponent } from './components/details/details.component';
import { CombatComponent } from './components/combat/combat.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent, canActivate: [authGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'pokedex', component: PokedexComponent, canActivate: [authGuard]},
  {path: 'details/:id', component: DetailsComponent, canActivate: [authGuard]},
  {path: 'combat', component: CombatComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
