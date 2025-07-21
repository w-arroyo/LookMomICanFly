import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpContextToken, HttpContext } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication/authentication.service';

const BYPASS= new HttpContextToken<boolean>(()=> false);

export function publicEndpoint(): HttpContext{
    return new HttpContext().set(BYPASS,true);
}

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

    private authenticationService: AuthenticationService;

    constructor(authenticationService: AuthenticationService){
        this.authenticationService=authenticationService;
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        if(request.context.get(BYPASS)){
            return next.handle(request);
        }
        if(this.authenticationService.checkIfAuthenticated()){
            const token= this.authenticationService.getToken();
            request= request.clone({
                setHeaders:{
                    Authorization: `Bearer ${token}`
                }
            });
        }
        return next.handle(request);
    }

}