import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';

export const RoleGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean | UrlTree => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();

  if (token && authService.isAuthenticated()) {
    const requiredRoles = route.data['roles'] as string[];
    const userRole = authService.userRole(token);

    if (requiredRoles && requiredRoles.includes(userRole)) {
      return true; 
    } else {
      // Redirect to unauthorized page
      return router.parseUrl('/unauthorized');
    }
  } else {
    // Redirect to login page
    return router.parseUrl('/auth/login'); 
  }
};
