import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { Observable } from 'rxjs';
import { Sale } from '../../models/sale.model';
import { TransactionSummary } from '../../models/transaction_summary.model';
import { SuccessfullRequest } from '../../models/successful_request.model';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private httpClient:HttpClient;
  private baseUrl:string='/api/sales';
  private authenticationService:AuthenticationService;
  private userId:string='';

  constructor(httpClient:HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    const id=authenticationService.getUserId();
    if(id)
      this.userId=id;
  }

  findSale(saleId:string):Observable<Sale>{
    return this.httpClient.get<Sale>(`${this.baseUrl}/get/?saleId=${saleId}&userId=${this.userId}`);
  }

  findAll():Observable<TransactionSummary[]>{
    return this.httpClient.get<TransactionSummary[]>(`${this.baseUrl}/get-all/?userId=${this.userId}`);
  }

  newTrackingNumber(saleId:string):Observable<SuccessfullRequest>{
    return this.httpClient.post<SuccessfullRequest>(`${this.baseUrl}/new-sale-tracking/?saleId=${saleId}&userId=${this.userId}`,null);
  }

}
