import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequestData } from '../../models/login.model';
import { BehaviorSubject, catchError, map, Observable, throwError } from 'rxjs';
import { AuthenticationSuccess } from '../../models/authentication_success.model';
import { UserRegistration } from '../../models/register.model';
import { publicEndpoint } from '../../config/request.interceptor';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService{

  private httpClient: HttpClient;
  private URL: string= '/api/users';
  private tokenBehaviourSubject= new BehaviorSubject<string | null>(null);
  isLoggedSubject=new BehaviorSubject<boolean>(false);

  constructor(httpClient:HttpClient) {
    this.httpClient=httpClient;
    this.initializeService();
  }

  private initializeService(): void{
    const token= localStorage.getItem('jwt_token');
    if(token!==null){
      if(this.checkIfTokenIsValid(token)){
        this.tokenBehaviourSubject.next(token);
        this.isLoggedSubject.next(true);
      }
    }
  }

  login(credentials:LoginRequestData): Observable<any>{
    return this.checkCredentials(this.httpClient.post<AuthenticationSuccess>(`${this.URL}/login`, credentials, {context: publicEndpoint()}));
  }

  createNewAccount(credentials: UserRegistration): Observable<any>{
    return this.checkCredentials(this.httpClient.post<AuthenticationSuccess>(`${this.URL}/register`, credentials, {context: publicEndpoint()}));
  }

  private checkCredentials(observable:Observable<any>): Observable<any>{
    return observable.pipe(
      map(
        (response)=>{
          this.loginSuccessful(response.token);
        }
      ),
      catchError(
        (error)=>{
          const message= error.error?.error || 'Server error.';
          return throwError(()=>new Error(message));
        }
      )
    );
  }

  private loginSuccessful(token: string): void{
    localStorage.setItem('jwt_token', token);
    this.tokenBehaviourSubject.next(token);
    this.isLoggedSubject.next(true);
  }

  logout(): void{
    this.httpClient.post<AuthenticationSuccess>(`${this.URL}/logout`,null);
    // NEEDS IMPROVING 
    localStorage.removeItem('jwt_token');
    this.tokenBehaviourSubject.next(null);
    this.isLoggedSubject.next(false);
    window.location.href = '/home';
  }

  checkIfAuthenticated(): boolean{
    return this.getToken()!==null;
  }

  getToken(): string | null{
    return this.tokenBehaviourSubject.value;
  }

  private checkIfTokenIsValid(token:string):boolean{
    try {
      const decoded=jwtDecode<{exp: number}>(token);
      const currentTime=Date.now()/1000;
      return decoded.exp>currentTime;
    } catch (error) {
      console.log(error);
      return false;
    }
  }

  getUserId(){
    const token=this.getToken();
    if(token==null)
      return null;
    try{
      const decoded=jwtDecode<{sub: string}>(token);
      return decoded.sub;
    } catch(error){
      console.log(error);
      return null;
    }
  }

}
