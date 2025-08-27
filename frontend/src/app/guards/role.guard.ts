import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private router: Router, private jwtHelper: JwtHelperService) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');

    if (!token || this.jwtHelper.isTokenExpired(token)) {
      console.log('Token inválido o expirado');
      this.router.navigate(['/login']);
      return false;
    }

    const decodedToken: any = this.jwtHelper.decodeToken(token);
    const roles: string[] = decodedToken.roles;

    console.log('Rol detectado en guard:', roles);

    if (roles && roles.includes('ROLE_ADMIN')) {
      return true;
    }

    this.router.navigate(['/perfil']); // O redirige a una página de "no autorizado"
    return false;
  }
}
