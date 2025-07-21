import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProductDetailsService } from '../../../services/product_details/product-details.service';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { ProductSummary } from '../../../models/product_summary.model';
import { CommonModule } from '@angular/common';
import { LoadingScreenComponent } from '../loading-screen/loading-screen.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-liked-products-list',
  imports: [CommonModule,LoadingScreenComponent],
  templateUrl: './profile-liked-products-list.component.html',
  styleUrl: './profile-liked-products-list.component.css'
})
export class ProfileLikedProductsListComponent implements OnInit,OnDestroy{

  private productService:ProductDetailsService;
  private router:Router;
  products$!:Observable<ProductSummary[]>;
  private destroyStream:Subject<void>=new Subject<void>();

  error:string | null=null;

  constructor(productService:ProductDetailsService,router:Router){
    this.productService=productService;
    this.router=router;
  }

  ngOnInit(): void {
    this.getLikedProducts();
  }

  goToProduct(id:string,category:string){
    this.router.navigate([`/products/${category.toLowerCase()}/details/${id}`]);
  }

  private handleObservable(observable:Observable<any>){
    return observable.pipe(
      takeUntil(
      this.destroyStream
    ),
      catchError(
        (error)=>{
          console.log(error);
          this.error=error.error?.error;
          return throwError(()=> new Error(error.error?.error));
        }
      )
    );
  }

  private getLikedProducts(){
    this.products$=this.handleObservable(
      this.productService.getLikedProducts()
    );
  }

  unlike(id:string){
    this.productService.unlikeAProduct(id).pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next:(data)=> this.getLikedProducts(),
      error:(error)=>{
        this.error=error.error?.error;
      }
    });
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
