import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginRequestData } from '../../../models/login.model';
import { UserRegistration } from '../../../models/register.model';
import { AuthenticationService } from '../../../services/authentication/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, Subject, takeUntil } from 'rxjs';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';

@Component({
  selector: 'app-authentication',
  imports: [CommonModule, FormsModule, LoadingScreenComponent],
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.css'
})
export class AuthenticationComponent implements OnInit, OnDestroy {

  private authenticationService: AuthenticationService;
  private activatedRoute:ActivatedRoute;
  private router: Router;
  isLoginMode = true;
  loginCredentials: LoginRequestData;
  registrationCredentials: UserRegistration;
  emailInput!: string;
  passwordInput!: string;
  errorMessage: string='';
  errorsExist: boolean=false;
  loadingBehaviour:BehaviorSubject<boolean>=new BehaviorSubject<boolean>(true);
  loading$=this.loadingBehaviour.asObservable();
  private destroyStream$:Subject<void>=new Subject<void>();

  constructor(authenticationService: AuthenticationService, router:Router,activatedRoute:ActivatedRoute){
    this.authenticationService=authenticationService;
    this.router=router;
    this.loginCredentials=new LoginRequestData();
    this.registrationCredentials=new UserRegistration();
    this.activatedRoute=activatedRoute;
  }

  ngOnInit(): void {
    const param=this.activatedRoute.snapshot.paramMap.get('authentication');
    if(param!=null && param==='create-account')
      this.isLoginMode=false;
    this.loadingBehaviour.next(false);
  }

  switchMode(): void{
    this.isLoginMode = !this.isLoginMode;
    this.errorMessage = '';
    this.emailInput='';
    this.passwordInput='';
    this.registrationCredentials.name='';
    if(this.isLoginMode){
      this.router.navigate(['/authentication/login']);
    }
    else this.router.navigate(['/authentication/create-account']);
  }

  authenticate(): void{
    if(this.emailInput.trim()==='' || this.passwordInput.trim()===''){
      this.errorsExist=true;
      this.errorMessage='Empty fields are not allowed.'
    }
    else{
      if(this.isLoginMode){
        this.login();
      }
      else this.registration();
    }
  }

  private login(): void{
    this.loadingBehaviour.next(true);
    this.loginCredentials.setEmail(this.emailInput);
    this.loginCredentials.setPassword(this.passwordInput);
    this.manageObservable(this.authenticationService.login(this.loginCredentials));
  }

  private registration(): void{
    if(this.registrationCredentials.name.trim()===''){
      this.errorsExist=true;
      this.errorMessage='Empty fields are not allowed.'
    }
    else{
      this.loadingBehaviour.next(true);
      this.registrationCredentials.setEmail(this.emailInput);
      this.registrationCredentials.setPassword(this.passwordInput);
      this.manageObservable(this.authenticationService.createNewAccount(this.registrationCredentials));
    }
  }

  private manageObservable(observable:Observable<any>): void{
    observable.pipe(
      takeUntil(
        this.destroyStream$
      )
    )
    .subscribe({
      next: (data)=>{
        this.loadingBehaviour.next(false);
        this.router.navigate(['/account/profile']);
      },
      error: (error) =>{
        this.loadingBehaviour.next(false);
        this.errorsExist=true;
        this.errorMessage=error.message;
      }
    });
  }

  ngOnDestroy(): void {
    this.destroyStream$.next();
    this.destroyStream$.complete();
  }

}
