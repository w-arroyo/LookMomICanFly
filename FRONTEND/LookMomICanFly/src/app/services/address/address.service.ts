import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Address } from '../../models/address,model';
import { AuthenticationService } from '../authentication/authentication.service';
import { SuccessfullRequest } from '../../models/successful_request.model';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  
  private httpClient:HttpClient;
  private baseUrl:string='/api';
    private authService:AuthenticationService;
    private userId:string | null;

  constructor(httpClient:HttpClient,authService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authService=authService;
    this.userId=authService.getUserId();
  }

    getUserAddresses():Observable<Address[]>{
      return this.httpClient.get<Address[]>(`${this.baseUrl}/users/addresses/?userId=${this.userId}`);
    }
  
    saveAddress(address:Address): Observable<SuccessfullRequest>{
      address.userId= this.userId ? this.userId : '';
      return this.httpClient.post<SuccessfullRequest>(`${this.baseUrl}/addresses/save`,address);
    }

    deleteAddress(addressId:string): Observable<SuccessfullRequest>{
      return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/addresses/deactivate`,{id:addressId,userId:this.userId});
    }

}
