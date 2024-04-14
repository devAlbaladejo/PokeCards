import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { Users } from '../models/users';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  cryptoKey = "eLUhT4tE5LFcv!2pVd!rck3Fc";

  constructor(private snackBar: MatSnackBar) { }

  public encrypt(data: Users) : string{
    return CryptoJS.AES.encrypt(JSON.stringify(data), this.cryptoKey).toString();
  }

  decryptData(data: string): any {
    return JSON.parse(CryptoJS.AES.decrypt(data, this.cryptoKey).toString(CryptoJS.enc.Utf8));
  }

  showAlert(message: string, type: string){
    this.snackBar.open(message, type, {
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      duration:5000
    });
  }
}
