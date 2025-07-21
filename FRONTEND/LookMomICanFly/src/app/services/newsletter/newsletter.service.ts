import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SuccessfullRequest } from '../../models/successful_request.model';
import { HttpClient } from '@angular/common/http';
import { publicEndpoint } from '../../config/request.interceptor';

@Injectable({
  providedIn: 'root'
})
export class NewsletterService {

  private httpClient:HttpClient;

  constructor(httpClient:HttpClient) {
    this.httpClient=httpClient;
  }

  send(email:string):Observable<SuccessfullRequest>{
    return this.httpClient.post<SuccessfullRequest>(`/api/newsletter/?email=${email}`,null, {context: publicEndpoint()});
  }
}
