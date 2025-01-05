import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';

export const AuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean | UrlTree => {
  // Use Angular's inject() to access services
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();

  if (token && authService.isAuthenticated()) {

    return true;
  } else {
    router.navigate(['/auth/login']);
    return false;
  }
};
