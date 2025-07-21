import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { HttpClient } from '@angular/common/http';
import { TransactionSummary } from '../../models/transaction_summary.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private httpClient:HttpClient;
  private baseUrl:string='/api';
  private authenticationService:AuthenticationService;
  private userId:string;

  constructor(httpClient:HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    this.userId=authenticationService.getUserId() || '';
  }

  findAllSales(){
    return this.httpClient.get<TransactionSummary[]>(`${this.baseUrl}/sales/get-all/?userId=${this.userId}`)
  }

  findAllOrders(){
    return this.httpClient.get<TransactionSummary[]>(`${this.baseUrl}/orders/get-all/?userId=${this.userId}`)
  }

}
