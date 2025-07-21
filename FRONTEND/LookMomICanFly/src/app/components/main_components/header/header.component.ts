import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { AuthenticationService } from '../../../services/authentication/authentication.service';
import { Router, RouterLinkWithHref } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductSummaryService } from '../../../services/product_summary/product-summary.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterLinkWithHref, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  private httpClient: HttpClient;
  private authenticationService: AuthenticationService;
  private productSummaryService: ProductSummaryService;
  private router: Router;
  showCategories = false;
  searchActive = false;
  isExpanded = false;
  accountActive= false;
  headerHeight = 60;
  search: string='';
  categories$!: Observable<string[]>;
  isLogged$!: Observable<boolean>;

  constructor(httpClient: HttpClient, authenticationService:AuthenticationService, router: Router, productSummaryService: ProductSummaryService){
    this.httpClient=httpClient;
    this.authenticationService=authenticationService;
    this.router=router;
    this.productSummaryService=productSummaryService;
  }

  ngOnInit(): void{
    this.getCategories();
    this.isLogged$=this.authenticationService.isLoggedSubject.asObservable();
  }

  private getCategories(){
    this.categories$=this.productSummaryService.getCategories().pipe(
          catchError(
            (error) =>{
              const message= error.error?.error || 'Server error.';
              return throwError(()=>new Error(message));
            }
          )
        );
  }

  expandCategories(): void{
    this.showCategories = true;
    this.isExpanded = true;
    this.headerHeight = 320;
  }

  expandSearch(): void{
    this.search='';
    this.searchActive = true;
    this.isExpanded = true;
    this.headerHeight = 120;
  }

  expandAccount(): void{
      this.accountActive = true;
      this.isExpanded = true;
      this.headerHeight = 140;
  }

  collapseHeader(): void{
    this.showCategories = false;
    this.searchActive = false;
    this.isExpanded = false;
    this.headerHeight = 60;
  }

  logout(): void{
    this.authenticationService.logout();
    //this.router.navigate(['/home']);
  }

  redirectHome(): void{
    this.productSummaryService.emptyProductList();
    this.productSummaryService.currentCategorySubject.next(null);
    this.router.navigate(['/home']);
  }

  redirectToProducts(){
    this.productSummaryService.allProducts();
    this.productSummaryService.currentCategorySubject.next(null);
    this.router.navigate(['/products/all']);
  }

  redirectToBestSellers(): void{
    this.productSummaryService.productsByBestSellers();
    this.productSummaryService.currentCategorySubject.next(null);
    this.router.navigate(['/products/best-sellers'])
  }

  redirectToCategory(category: string): void{
    this.productSummaryService.productsByCategory(category);
    this.productSummaryService.currentCategorySubject.next(category);
    this.router.navigate([`/products/category/${category.toLowerCase()}`])
  }

  searchItems(): void{
    if(this.search.trim()!==''){
      this.productSummaryService.productsBySearch(this.search);
      this.productSummaryService.currentCategorySubject.next(null);
      this.router.navigate([`/products/search/${this.search}`])    
    }
  }

}
