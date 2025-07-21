import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { Observable } from 'rxjs';
import { Order } from '../../models/order.model';
import { TransactionSummary } from '../../models/transaction_summary.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private httpClient:HttpClient;
  private baseUrl:string='/api/orders';
  private authenticationService:AuthenticationService;
  private userId:string='';

  constructor(httpClient:HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    const id=authenticationService.getUserId();
    if(id)
      this.userId=id;
  }

  findOrder(orderId:string):Observable<Order>{
    return this.httpClient.get<Order>(`${this.baseUrl}/get/?orderId=${orderId}&userId=${this.userId}`)
  }

    findAll():Observable<TransactionSummary[]>{
      return this.httpClient.get<TransactionSummary[]>(`${this.baseUrl}/get-all/?userId=${this.userId}`);
    }

}
