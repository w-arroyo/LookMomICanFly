import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { publicEndpoint } from '../../config/request.interceptor';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private httpClient: HttpClient;
  private baseUrl:string='/api';
  productPostDetailsSubject:BehaviorSubject<Observable<SuccessfullRequest>[]>=new BehaviorSubject<Observable<SuccessfullRequest>[]>([]);
  selectedSizeSubject:BehaviorSubject<string | null>=new BehaviorSubject<string | null>(null);
  selectedSize$:Observable<string | null>=this.selectedSizeSubject.asObservable();

  constructor(httpClient: HttpClient) {
    this.httpClient=httpClient;
  }

  getProductSizes(productId:string): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.baseUrl}/products/get/sizes/?productId=${productId}`, {context: publicEndpoint()});
  }

  findProductLastSaleBySize(productId:string, size:string): Observable<SuccessfullRequest>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}/sales/get/last/?productId=${productId}&size=${size}`, {context: publicEndpoint()});
  }

}
