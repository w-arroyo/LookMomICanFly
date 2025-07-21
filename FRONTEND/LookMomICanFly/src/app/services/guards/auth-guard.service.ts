import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication.service';
import { map, take } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  private authenticationService:AuthenticationService;
  private router:Router;

  constructor(authenticationService:AuthenticationService, router:Router) {
    this.authenticationService=authenticationService;
    this.router=router;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.authenticationService.isLoggedSubject.pipe(
      take(1) // this takes the latest value and unsuscribes itself
      ,map(
        isLogged=>{
          if(isLogged)
            return true;
          else{
            return this.router.createUrlTree(['authentication/login'],{
              queryParams: {
                returnUrl: state.url
              }
            }
            )
          }
        }
      )
    );
  }

}
