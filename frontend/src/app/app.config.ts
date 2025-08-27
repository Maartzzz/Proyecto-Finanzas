import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';

export function jwtOptionsFactory() {
  return {
    tokenGetter: () => localStorage.getItem('token'),
    allowedDomains: ['localhost:8080'], // Ajusta si usas otro backend
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    JwtHelperService,
    {
      provide: JWT_OPTIONS,
      useFactory: jwtOptionsFactory
    },
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient()
  ]
};
