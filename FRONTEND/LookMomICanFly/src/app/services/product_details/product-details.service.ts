import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sneakers } from '../../models/sneakers.model';
import { Product } from '../../models/product.model';
import { publicEndpoint } from '../../config/request.interceptor';
import { ProductSummary } from '../../models/product_summary.model';
import { AuthenticationService } from '../authentication/authentication.service';
import { SuccessfulBooleanRequest } from '../../models/successful_boolean_request.model';
import { SuccessfullRequest } from '../../models/successful_request.model';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsService {

  private baseUrl: string='/api/products';
  private authenticationService:AuthenticationService;
  private httpClient:HttpClient;
  userId:string='';

  constructor(httpClient:HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    const id=this.authenticationService.getUserId();
    if(id)
      this.userId=id;
  }

  checkIfUserIsLogged(){
    const id=this.authenticationService.getUserId();
    if(id){
      this.userId=id;
      return true;
    }
    return false;
  }

  loadProductPage(id:string,category:string): Observable<Product>{
    return this.httpClient.get<Sneakers>(`${this.baseUrl}/${category}/get/?id=${id}`, {context: publicEndpoint()})
  }

  loadProductSummary(id:string):Observable<ProductSummary>{
    return this.httpClient.get<ProductSummary>(`${this.baseUrl}/get/?productId=${id}`, {context:publicEndpoint()})
  }

  checkIfUserLikesAProduct(productId:string): Observable<SuccessfulBooleanRequest>{
    return this.httpClient.get<SuccessfulBooleanRequest>(`${this.baseUrl}/favorites/check/?userId=${this.userId}&productId=${productId}`)
  }

  getLikedProducts():Observable<ProductSummary[]>{
    return this.httpClient.get<ProductSummary[]>(`${this.baseUrl}/favorites/list/?userId=${this.userId}`);
  }

  likeAProduct(productId:string): Observable<SuccessfullRequest>{
    return this.httpClient.post<SuccessfullRequest>(`${this.baseUrl}/favorites/like/?userId=${this.userId}&productId=${productId}`,null)
  }

  unlikeAProduct(productId:string): Observable<SuccessfullRequest>{
    return this.httpClient.put<SuccessfullRequest>(`${this.baseUrl}/favorites/unlike/?userId=${this.userId}&productId=${productId}`,null)
  }

}
