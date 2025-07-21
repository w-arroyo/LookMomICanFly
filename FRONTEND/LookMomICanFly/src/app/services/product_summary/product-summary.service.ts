import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, catchError, throwError } from 'rxjs';
import { ProductSummary } from '../../models/product_summary.model';
import { publicEndpoint } from '../../config/request.interceptor';
import { FilterSelectedOptions } from '../../models/filter_options.model';

@Injectable({
  providedIn: 'root'
})
export class ProductSummaryService {

  private baseUrl: string='/api/products';
  private httpClient: HttpClient;
  productListBehaviorSubject=new BehaviorSubject<ProductSummary[]>([]);
  currentCategorySubject:BehaviorSubject<string | null>=new BehaviorSubject<string | null>(null);

  constructor(httpClient: HttpClient) { 
    this.httpClient=httpClient;
  }

  getCategories(): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.baseUrl}/categories/`, {context: publicEndpoint()});
  }

  allProducts(){
    this.handleBackendResponse(
      this.httpClient.get<ProductSummary[]>(`${this.baseUrl}/get/all-summary`, {context: publicEndpoint()})
    );
  }

  productsByCategory(category:string): void{
    this.handleBackendResponse(
      this.httpClient.get<ProductSummary[]>(`${this.baseUrl}/get/all-summary-by-category/?category=${category}`, {context: publicEndpoint()})
    );
  }

  productsBySearch(productName: string): void{
    this.handleBackendResponse(
      this.httpClient.get<ProductSummary[]>(`${this.baseUrl}/find/?name=${productName}`, {context: publicEndpoint()})
    );
  }

  productsByBestSellers(): void{
    this.handleBackendResponse(
      this.httpClient.get<ProductSummary[]>(`${this.baseUrl}/best-sellers`, {context: publicEndpoint()})
    );
  }

  private handleBackendResponse(response: Observable<ProductSummary[]>): void{
    response.pipe(
      catchError(
        (error) =>{
          this.emptyProductList();
          const message= error.error?.error || 'Server error.';
          return throwError(()=>new Error(message));
        }
      )
    ).subscribe({
      next: (response) => {
        this.productListBehaviorSubject.next(response);
      },
      error: (error) => console.log(error.message)
    });
  }

  emptyProductList(){
    this.productListBehaviorSubject.next([]);
  }

  getFilteredProducts(filter:FilterSelectedOptions){
    this.handleBackendResponse(
      this.httpClient.post<ProductSummary[]>(`${this.baseUrl}/get/filter`,filter)
    );
  }

}
