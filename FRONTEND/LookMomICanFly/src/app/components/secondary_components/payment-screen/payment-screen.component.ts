import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { loadStripe } from '@stripe/stripe-js';
import { BidService } from '../../../services/bid/bid.service';
import { PaymentService } from '../../../services/payment/payment.service';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { Bid } from '../../../models/bid.model';
import { TransactionSuccess } from '../../../models/transaction_completed.model';
import { BidDetails } from '../../../models/full_bid.model';
import { ProductDetailsService } from '../../../services/product_details/product-details.service';
import { ProductSummary } from '../../../models/product_summary.model';
import { SuccessfullRequest } from '../../../models/successful_request.model';
import { ShippingOptionService } from '../../../services/shipping_option/shipping-option.service';

@Component({
  selector: 'app-payment-screen',
  imports: [CommonModule],
  templateUrl: './payment-screen.component.html',
  styleUrl: './payment-screen.component.css'
})
export class PaymentScreenComponent implements OnInit, OnDestroy, AfterViewInit{

  private bidService:BidService;
  private paymentService:PaymentService;
  private productService: ProductDetailsService;
  private router:Router;
  private shippingOptionService: ShippingOptionService;
  product$!:Observable<ProductSummary>;
  operationalFee$!: Observable<SuccessfullRequest>;
  shippingPrice$!:Observable<SuccessfullRequest>;
  bid!:Bid;
  totalAmount!:number;
  private destroyStream:Subject<void>=new Subject<void>();

  stripe:any;
  cardElement:any;
  loading:boolean=false;
  errorMessage:string | null =null;
  paymentSuccess:boolean=false;

  constructor(bidService:BidService,paymentService:PaymentService,router:Router,productService: ProductDetailsService,shippingOptionService: ShippingOptionService) {
    this.bidService=bidService;
    this.paymentService=paymentService;
    this.router=router;
    this.productService=productService;
    this.shippingOptionService = shippingOptionService;
    if(paymentService.bid==null || paymentService.totalAmount==null){
      router.navigate(['account/profile']);
    }
    this.bid=paymentService.bid;
    this.totalAmount=paymentService.totalAmount;
  }

  async ngOnInit() {
    this.getProduct();
    this.getOperationalFee();
    this.getShippingPrice();
  }

  ngAfterViewInit(): void {
    this.loadStripeElements();
  }

  private async loadStripeElements(){
    try{
      this.stripe=await loadStripe('pk_test_51R6z71D1xEOCywLR3CmqkBdDItiERNA2sLajnrxBVKVWSEpT44fCksfZtfOKzBuP4Xl15r2zUlDPwuNgjsrkd5ec00zQm19oi6');
      const elements=this.stripe.elements();
      this.cardElement=elements.create('card'); 
      const cardElement=document.getElementById('card-element'); // this would throw an error if it was non existent
      if(cardElement){
        this.cardElement.mount('#card-element');
      } else{
        console.error('#card-element not found in DOM');
        setTimeout(
          () => this.loadStripeElements(), 300
        );
      }
    } catch(error) {
      console.error('Stripe error:', error);
    }
  }

  private handleObservable(observable:Observable<any>):Observable<any>{
    return observable.pipe(
      takeUntil(
        this.destroyStream
      ),
      catchError(
        (error)=>{
          console.log(error);
          this.router.navigate(['home']);
          return throwError(()=> new Error(error.error?.error));
        }
      )
    );
  }

  private getProduct(){
    this.product$=this.handleObservable(
      this.productService.loadProductSummary(this.bid.productId)
    );
  }

  private getOperationalFee() {
    this.operationalFee$ = this.handleObservable(
      this.bidService.getOperationalFee()
    );
  }

  private getShippingPrice(){
    this.shippingPrice$=this.handleObservable(
      this.shippingOptionService.getShippingPrice(this.bid.shippingOptionId)
    );
  }

  getImage(productName:string | undefined){
    return `${productName}.jpg`;
  }

  async submitPayment(){
    this.loading=true;
    this.errorMessage=null;
    try {
      const { error,paymentMethod }=await this.stripe.createPaymentMethod({
        type: 'card',
        card: this.cardElement,
      });
      if(error){
        this.errorMessage=error.message;
        this.loading=false;
        return;
      }
      this.getPaymentIntent();
    }catch(error){
      this.errorMessage='An error occurred while processing your payment';
      this.loading=false;
    }
  }

  private getPaymentIntent(){
    this.paymentService.getPaymentIntent(this.bidService.userId).pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next: (data)=>{
        this.bid.paymentIntentId=data.paymentIntentId;
        this.postBid();
      },
      error: (error)=>{
        this.loading=false;
        this.errorMessage=error.error?.error;
      }
    });
  }

  private postBid(){
    this.bidService.saveBid(this.bid).pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next:(data)=>{
        let id;
        if('reference' in data){
          id = (data as TransactionSuccess).id;
          this.router.navigate(['orders/'+id]);
        } else{
          id=(data as BidDetails).id;
          this.router.navigate(['bids/'+id]);
        }
      },
      error:(error)=>{
        const message:string=error.error?.error;
        if(message.includes("declined"))
          this.errorMessage='Your card was declined. Try again with another one.';
        else this.errorMessage=message;
        this.loading=false;
      }
    });
  
    }

    ngOnDestroy():void{
      this.destroyStream.next();
      this.destroyStream.complete();
    }

}
