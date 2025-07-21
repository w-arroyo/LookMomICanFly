import { CommonModule } from '@angular/common';
import { Component, EventEmitter, HostListener, OnDestroy, OnInit, Output } from '@angular/core';
import { ProductSummaryService } from '../../../services/product_summary/product-summary.service';
import { ProductSummaryDetailsComponent } from '../../secondary_components/product-summary-details/product-summary-details.component';
import { BehaviorSubject, combineLatest, map, Observable, Subject, takeUntil, tap } from 'rxjs';
import { ProductSummary } from '../../../models/product_summary.model';
import { ActivatedRoute, Router } from '@angular/router';
import { FilterProductsComponent } from '../../secondary_components/filter-products/filter-products.component';
import { FilterProductsService } from '../../../services/filter_products/filter-products.service';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';

@Component({
  selector: 'app-product-summary-list',
  imports: [CommonModule, ProductSummaryDetailsComponent, FilterProductsComponent, LoadingScreenComponent],
  templateUrl: './product-summary-list.component.html',
  styleUrl: './product-summary-list.component.css'
})
export class ProductSummaryListComponent implements OnInit, OnDestroy {

  private productSummaryService: ProductSummaryService;
  productList$: Observable<ProductSummary[]>;
  private MAX_PRODUCTS_SHOWN: number=12;
  private currentShownProductsRangeSubject: BehaviorSubject<number[]>=new BehaviorSubject<number[]>([0,this.MAX_PRODUCTS_SHOWN]);
  currentShownProducts$:Observable<any>;
  private activatedRoute: ActivatedRoute;
  private router:Router;
  private filterProductsService:FilterProductsService;
  private destroyStream: Subject<void>=new Subject<void>();
  maxPages!:number;
  currentPage: number=1;
  showScrollButton: boolean = false;

  constructor(productSummaryService: ProductSummaryService, activatedRoute: ActivatedRoute, filterProductsService:FilterProductsService,router:Router){
    this.productSummaryService=productSummaryService; 
    this.activatedRoute=activatedRoute;
    this.filterProductsService=filterProductsService;
    this.router=router;
    this.productList$=productSummaryService.productListBehaviorSubject.asObservable().pipe(
      tap(
        (products)=>{
          this.maxPages=Math.ceil(products.length/this.MAX_PRODUCTS_SHOWN);
          if(this.currentPage>1){
            this.currentPage=1;
            this.currentShownProductsRangeSubject.next([0,this.MAX_PRODUCTS_SHOWN]);
          }
        }
      )
    );

    this.currentShownProducts$ = combineLatest([
      this.productList$,
      this.currentShownProductsRangeSubject
    ]).pipe(
      map(([products, range]) => products.slice(range[0], range[1]))
    );
  }

  ngOnInit(): void {
    this.checkScrollPosition();
    if(this.productSummaryService.productListBehaviorSubject.value.length==0){
      this.activatedRoute.url.pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe(
      parts=>{
        this.filterProducts(parts.map(part => part.path).join('/'));
      }
    );
    }
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    this.checkScrollPosition();
  }

  private checkScrollPosition() {
    this.showScrollButton = window.pageYOffset > 300;
  }

  scrollToTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }

  private filterProducts(path:string):void{
    if(path.includes('category')){
      const category=this.activatedRoute.snapshot.paramMap.get('category');
      if(category!=null && category.trim()!==''){
        this.productSummaryService.productsByCategory(category);
        this.productSummaryService.currentCategorySubject.next(category);
      }
    }
    else if(path.includes('search')){
      const productName=this.activatedRoute.snapshot.paramMap.get('product-name');
      if(productName!=null && productName.trim()!==''){
        this.productSummaryService.productsBySearch(productName);
      }
    }
    else if(path.includes('best-sellers')){
      this.productSummaryService.productsByBestSellers();
    }
    else if(path.includes('all')){
      this.productSummaryService.allProducts();
    }
  }

  activateFilterComponent() {
    this.filterProductsService.isVisibleSubject.next(true);
  }

  onFilterClose() {
    this.filterProductsService.isVisibleSubject.next(false);
  }

  previousPage(){
    if(this.currentPage>1){
      this.currentPage--;
      const currentRange=this.currentShownProductsRangeSubject.value;
      this.currentShownProductsRangeSubject.next([currentRange[0]-this.MAX_PRODUCTS_SHOWN,currentRange[1]-this.MAX_PRODUCTS_SHOWN]);
      this.scrollToTop();
    }
  }

  nextPage(){
    if(this.currentPage+1<=this.maxPages){
      this.currentPage++;
      const currentRange=this.currentShownProductsRangeSubject.value;
      this.currentShownProductsRangeSubject.next([currentRange[0]+this.MAX_PRODUCTS_SHOWN,currentRange[1]+this.MAX_PRODUCTS_SHOWN]);
      this.scrollToTop();
    }
  }

  goToProduct(id:string,category:string){
    this.router.navigate([`/products/${category.toLowerCase()}/details/${id}`]);
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
