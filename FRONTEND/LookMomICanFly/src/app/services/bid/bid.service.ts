import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { publicEndpoint } from '../../config/request.interceptor';
import { Bid } from '../../models/bid.model';
import { BidDetails } from '../../models/full_bid.model';
import { TransactionSuccess } from '../../models/transaction_completed.model';
import { AuthenticationService } from '../authentication/authentication.service';
import { PostSummary } from '../../models/post_summary.model';
import { UpdatePost } from '../../models/update_post.model';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  private httpClient: HttpClient;
  private baseUrl:string='/api/bids/';
  private authenticationService:AuthenticationService;
  userId:string='';

  constructor(httpClient: HttpClient,authenticationService:AuthenticationService) {
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    const id=authenticationService.getUserId();
    if(id){
      this.userId=id;
    }
  }

  findHighestBidByProductIdAndSize(productId:string, size:string): Observable<SuccessfullRequest>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}highest-bid/?productId=${productId}&size=${size}`, {context: publicEndpoint()});
  }

  getOperationalFee():Observable<SuccessfullRequest>{
    return this.httpClient.get<SuccessfullRequest>(`${this.baseUrl}get/operational`, {context:publicEndpoint()});
  }

  saveBid(bid:Bid):Observable<BidDetails | TransactionSuccess>{
    bid.userId=this.userId;
    return this.httpClient.post<BidDetails | TransactionSuccess>(`${this.baseUrl}save`, bid);
  }

  findBid(bidId:string):Observable<BidDetails>{
    return this.httpClient.get<BidDetails>(`${this.baseUrl}get/?bidId=${bidId}&userId=${this.userId}`);
  }

  findAll():Observable<PostSummary[]>{
    return this.httpClient.get<PostSummary[]>(`${this.baseUrl}get-all/?userId=${this.userId}`);
  }

  updateBid(post:UpdatePost):Observable<BidDetails | TransactionSuccess>{
    post.userId=this.userId;
    return this.httpClient.post<BidDetails | TransactionSuccess>(`${this.baseUrl}update`, post);
  }

}
