import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Payment } from '../../models/payment.model';
import { Observable } from 'rxjs';
import { Bid } from '../../models/bid.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private httpClient:HttpClient;
  private url:string='/api/payments/create';
  bid!:Bid;
  totalAmount!:number;

  constructor(httpClient:HttpClient) {
    this.httpClient=httpClient;
  }

  getPaymentIntent(userId:string):Observable<Payment>{
    return this.httpClient.post<Payment>(`${this.url}/?userId=${userId}`,null);
  }

}
