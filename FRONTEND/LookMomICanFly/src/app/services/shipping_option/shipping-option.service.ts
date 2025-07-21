import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ShippingOption } from '../../models/shipping_option.model';
import { Observable } from 'rxjs';
import { publicEndpoint } from '../../config/request.interceptor';
import { SuccessfullRequest } from '../../models/successful_request.model';

@Injectable({
  providedIn: 'root'
})
export class ShippingOptionService {

  private httpClient:HttpClient;
  private baseUrl='/api/shipping-options';

  constructor(httpClient:HttpClient) {
    this.httpClient=httpClient;
  }

  getShippingOptions(): Observable<ShippingOption[]>{
    return this.httpClient.get<ShippingOption[]>(`${this.baseUrl}/get-all`, {context: publicEndpoint()});
  }

  getShippingPrice(id:string): Observable<SuccessfullRequest>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}/get-price/?id=${id}`, {context: publicEndpoint()});
  }

}
