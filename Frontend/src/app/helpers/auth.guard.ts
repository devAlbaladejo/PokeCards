import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  
  const router: Router = inject(Router);

  if(localStorage.getItem('token')){
    return true;
  }

  router.navigate(['/login']);

  return false;
};
