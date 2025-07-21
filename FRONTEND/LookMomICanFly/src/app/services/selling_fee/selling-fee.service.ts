import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { SellingFee } from '../../models/selling_fee.model';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { publicEndpoint } from '../../config/request.interceptor';

@Injectable({
  providedIn: 'root'
})
export class SellingFeeService {

  private httpClient:HttpClient;
  private authenticationService:AuthenticationService;
  private userId:string|null;

  constructor(httpClient:HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    this.userId=authenticationService.getUserId();
  }

  getCurrentSellingFee(){
    return this.httpClient.get<SellingFee>(`/api/fees/default/?userId=${this.userId}`);
  }

  getShippingFee(){
    return this.httpClient.get<SuccessfullRequest>(`/api/fees/shipping`, {context:publicEndpoint()});
  }

}
