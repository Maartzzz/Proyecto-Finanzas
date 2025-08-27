import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import { JwtHelperService } from '@auth0/angular-jwt';

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    provideHttpClient(
      withInterceptors([
        (req, next) => {
          const token = localStorage.getItem('token');
          if (token) {
            const authReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${token}`
              }
            });
            return next(authReq);
          }
          return next(req);
        }
      ])
    ),
    JwtHelperService,
    ...(appConfig.providers || [])
  ]
});
