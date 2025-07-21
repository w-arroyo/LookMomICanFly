import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { catchError, Observable, throwError } from 'rxjs';
import { publicEndpoint } from '../../config/request.interceptor';
import { AuthenticationService } from '../authentication/authentication.service';
import { Ask } from '../../models/ask.model';
import { AskDetails } from '../../models/full_ask.model';
import { TransactionSuccess } from '../../models/transaction_completed.model';
import { PostSummary } from '../../models/post_summary.model';
import { UpdatePost } from '../../models/update_post.model';

@Injectable({
  providedIn: 'root'
})
export class AskService {

  private baseUrl: string ='/api/asks';
  private httpClient: HttpClient;
  private authenticationService:AuthenticationService;
  private userId:string | null;

  constructor(httpClient: HttpClient,authenticationService:AuthenticationService) { 
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    this.userId=authenticationService.getUserId();
  }

  getLowestAskAmount(productId: string): Observable<any>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}/get/lowest-ask/?productId=${productId}`);
  }

  findLowestAskByProductIdAndSize(productId:string, size:string): Observable<SuccessfullRequest>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}/lowest-ask/?productId=${productId}&size=${size}`, {context: publicEndpoint()});
  }

  saveAsk(ask:Ask): Observable<AskDetails | TransactionSuccess>{
    if(this.userId)
      ask.userId=this.userId;
    return this.httpClient.post<AskDetails | TransactionSuccess>(`${this.baseUrl}/save`,ask)
  }

  findAsk(askId:string): Observable<AskDetails>{
    return this.httpClient.get<AskDetails>(`${this.baseUrl}/get/?userId=${this.userId}&askId=${askId}`);
  }

  findAll():Observable<PostSummary[]>{
    return this.httpClient.get<PostSummary[]>(`${this.baseUrl}/get-all/?userId=${this.userId}`);
  }

  updateAsk(post:UpdatePost): Observable<AskDetails | TransactionSuccess>{
    post.userId=this.userId || '';
    return this.httpClient.put<AskDetails | TransactionSuccess>(`${this.baseUrl}/update`,post);
  }

}
