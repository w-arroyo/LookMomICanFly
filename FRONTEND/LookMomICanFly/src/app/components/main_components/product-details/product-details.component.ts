import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, Type } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDetailsService } from '../../../services/product_details/product-details.service';
import { BehaviorSubject, catchError, Observable, Subject, takeUntil } from 'rxjs';
import { Sneakers } from '../../../models/sneakers.model';
import { PostTableComponent } from '../../secondary_components/post-table/post-table.component';
import { PostService } from '../../../services/post/post.service';
import { SuccessfullRequest } from '../../../models/successful_request.model';
import { Product } from '../../../models/product.model';
import { Clothing } from '../../../models/clothing.model';
import { Accessory } from '../../../models/accessory.model';
import { Collectible } from '../../../models/collectible.model';
import { Electronic } from '../../../models/electronic.model';
import { Football } from '../../../models/football.model';
import { Skateboard } from '../../../models/skateboard.model';
import { Music } from '../../../models/music.model';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';

@Component({
  selector: 'app-product-details',
  imports: [CommonModule, PostTableComponent,LoadingScreenComponent],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent implements OnInit, OnDestroy{

  private router:Router;
  private activatedRoute:ActivatedRoute;
  private productDetailsService:ProductDetailsService;
  postService:PostService;
  product$!:Observable<Product>;
  private destroyStream:Subject<void>=new Subject<void>();
  productPostDetails$:Observable<Observable<SuccessfullRequest>[]>;
  activeTab:string='details';
  private productId!:string;

  isLikedBehaviourSubject:BehaviorSubject<boolean>=new BehaviorSubject<boolean>(false);
  isLiked$:Observable<boolean>=this.isLikedBehaviourSubject.asObservable();

  selectedSize: string | null = null;

  constructor(router:Router,productDetailsService:ProductDetailsService,activatedRoute:ActivatedRoute,postService:PostService){
    this.router=router;
    this.productDetailsService=productDetailsService;
    this.activatedRoute=activatedRoute;
    this.postService=postService;
    this.productPostDetails$=this.postService.productPostDetailsSubject.asObservable();
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe(
      params=>{
        const category=params.get('category');
        const productId=params.get('productId');
        if(category && productId){
          this.productId=productId;
          this.product$=this.productDetailsService.loadProductPage(productId,category.toLowerCase()).pipe(
            catchError(
              (error)=>{
                console.log(error.error?.error);
                this.goToHomeScreen();
                throw(()=>new Error(error.error?.error));
              }
            )
          );
          this.checkIfLiked();
        }
        else this.goToHomeScreen();
      }
    );
  }

  private checkIfLiked(){
    if(this.productDetailsService.checkIfUserIsLogged()){
      this.productDetailsService.checkIfUserLikesAProduct(this.productId).pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe({
        next: (data)=>{
          this.isLikedBehaviourSubject.next(data.status);
        },
        error:(error)=>{
          console.log(error.error?.error);
          this.isLikedBehaviourSubject.next(false);
        }
      });
    }
  }

  private handleLikingRequest(observable:Observable<SuccessfullRequest>,boolean:boolean){
    return observable.pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next: (data)=>{
        this.isLikedBehaviourSubject.next(boolean);
      },
      error:(error)=>{
        console.log(error.error?.error);
      }
    })
  }

  goToHomeScreen(): void{
    this.router.navigate(['home']);
  }

  getProductImage(name:string){
    return `${name}.jpg`
  }

  toggleLike(): void {
    if(this.productDetailsService.checkIfUserIsLogged()){
      if(this.isLikedBehaviourSubject.value)
        this.handleLikingRequest(
          this.productDetailsService.unlikeAProduct(this.productId),false
        );
        else this.handleLikingRequest(
          this.productDetailsService.likeAProduct(this.productId),true
        );
    }
  }

  selectSize(size: string): void {
    this.selectedSize = size;
  }

  getProductField(product:Product){
    switch(product.category.toUpperCase()){
      case 'SNEAKERS':
        return 'SKU: '+(product as Sneakers).sku;
      case 'CLOTHING':
        return 'Season: '+(product as Clothing).season;
      case 'ACCESSORY':
        return 'Material: '+(product as Accessory).material;
      case 'COLLECTIBLE':
        return 'Collection: '+(product as Collectible).collectionName;
      case 'ELECTRONIC':
        return 'Handling: '+(product as Electronic).caution;
      case 'FOOTBALL':
        return 'Section: '+(product as Football).scope;
      case 'SKATEBOARD':
        return 'Length: '+(product as Skateboard).length;
      case 'MUSIC':
        return 'Format: '+(product as Music).format;
      default: 
      return '';
    }
  }

  createAsk(size:string){
    if(this.productId && size)
      this.router.navigate([`/asks/create/product/${this.productId}/size/${size}`]);
  }

  createBid(size:string){
    if(this.productId && size){
      this.router.navigate([`/bids/create/product/${this.productId}/size/${size}`]);
    }
      
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
