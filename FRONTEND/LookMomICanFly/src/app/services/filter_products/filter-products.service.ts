import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { publicEndpoint } from '../../config/request.interceptor';

@Injectable({
  providedIn: 'root'
})
export class FilterProductsService {

  private baseUrl: string='/api/products';
  isVisibleSubject:BehaviorSubject<boolean>=new BehaviorSubject<boolean>(false);
  isVisible$:Observable<boolean>= this.isVisibleSubject.asObservable();
  private httpClient:HttpClient;

  constructor(httpClient:HttpClient) {
    this.httpClient=httpClient;
  }

  getReleaseYears(): Observable<number[]>{
    return this.httpClient.get<number[]>(`${this.baseUrl}/get/years`, {context: publicEndpoint()});
  }

  getDifferentColors(): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.baseUrl}/get/colors`, {context: publicEndpoint()});
  }

  getDifferentManufacturers(): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.baseUrl}/get/manufacturers`, {context: publicEndpoint()});
  }

  getCategorySubcategories(category:string): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.baseUrl}/categories/subcategories/?category=${category}`, {context: publicEndpoint()});
  }

}
