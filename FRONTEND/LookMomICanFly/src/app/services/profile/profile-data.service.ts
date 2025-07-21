import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { UserProfileData } from '../../models/user_profile.model';
import { SellingFee } from '../../models/selling_fee.model';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router } from '@angular/router';
import { BankAccount } from '../../models/bank_account.model';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { PhoneNumberFormat } from '../../models/phone_number_format.model';
import { publicEndpoint } from '../../config/request.interceptor';
import { PhoneNumber } from '../../models/phone_number.model';
import { SavePhoneNumber } from '../../models/save_phone_number.model';

@Injectable({
  providedIn: 'root'
})
export class ProfileDataService {

  private httpClient:HttpClient;
  private router:Router;
  private baseUrl:string='/api';
  private authService:AuthenticationService;
  private userId:string | null;

  constructor(httpClient:HttpClient,authService:AuthenticationService,router:Router) {
    this.httpClient=httpClient;
    this.authService=authService;
    this.router=router;
    this.userId=authService.getUserId();
    if(this.userId==null){
      authService.logout();
      router.navigate(['home']);
    }
  }

  getProfileData():Observable<UserProfileData>{
    return this.httpClient.get<UserProfileData>(`${this.baseUrl}/users/get/?id=${this.userId}`);
  }

  getUserLevel(): Observable<SellingFee>{
    return this.httpClient.get<SellingFee>(`${this.baseUrl}/fees/level/?userId=${this.userId}`);
  }

  getBankAccount(): Observable<BankAccount | null>{
    return this.httpClient.get<BankAccount | null>(`${this.baseUrl}/bank-accounts/user/?userId=${this.userId}`);
  }

  deleteBankAccount(): Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/bank-accounts/deactivate/?user=${this.userId}`, null);
  }

  getPhonePrefixes(): Observable<PhoneNumberFormat[]>{
    return this.httpClient.get<PhoneNumberFormat[]>(`${this.baseUrl}/phone-numbers/formats`, {context: publicEndpoint()});
  }

  getPhoneNumber(): Observable<PhoneNumber | null>{
    return this.httpClient.get<PhoneNumber | null>(`${this.baseUrl}/phone-numbers/user/?userId=${this.userId}`);
  }

  deletePhoneNumber():Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/phone-numbers/deactivate/?user=${this.userId}`, null);
  }

  updateEmail(newEmail:string):Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/users/changeEmail`,{userId:this.userId, newField:newEmail});
  }

  updatePassword(oldPassword:string,newPassword:string):Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/users/change-password`,{id:this.userId, oldPassword:oldPassword,newPassword:newPassword});
  }

  deactivateAccount(): Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/users/deactivate/?userId=${this.userId}`, null)
    .pipe(
      tap(
        (data)=>{
          this.authService.logout();
        }
      )
    );
  }

  savePhoneNumber(prefix:string,number:string){
    const phone=new SavePhoneNumber();
    phone.digits=number;
    phone.prefix=prefix;
    if(this.userId!=null)
      phone.userId=this.userId;
    return this.httpClient.post<PhoneNumber>(`${this.baseUrl}/phone-numbers/save`, phone);
  }

  saveBankAccount(bankAccountNumber:string){
    const bankAccount=new BankAccount();
    bankAccount.number=bankAccountNumber;
    if(this.userId!=null)
      bankAccount.userId=this.userId;
    return this.httpClient.post<BankAccount>(`${this.baseUrl}/bank-accounts/save`, bankAccount);
  }

}
