import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AskService } from '../../../services/ask/ask.service';
import { BidService } from '../../../services/bid/bid.service';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { ProductDetailsService } from '../../../services/product_details/product-details.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSummary } from '../../../models/product_summary.model';
import { Address } from '../../../models/address,model';
import { BankAccount } from '../../../models/bank_account.model';
import { PhoneNumber } from '../../../models/phone_number.model';
import { SellingFee } from '../../../models/selling_fee.model';
import { AddressService } from '../../../services/address/address.service';
import { ProfileDataService } from '../../../services/profile/profile-data.service';
import { SellingFeeService } from '../../../services/selling_fee/selling-fee.service';
import { SuccessfullRequest } from '../../../models/successful_request.model';
import { Ask } from '../../../models/ask.model';
import { AskDetails } from '../../../models/full_ask.model';
import { TransactionSuccess } from '../../../models/transaction_completed.model';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';

@Component({
  selector: 'app-create-ask',
  imports: [CommonModule,FormsModule, ReactiveFormsModule,LoadingScreenComponent],
  templateUrl: './create-ask.component.html',
  styleUrl: './create-ask.component.css'
})
export class CreateAskComponent implements OnInit, OnDestroy{

    private askService:AskService;
    private bidService:BidService;
    private productService:ProductDetailsService;
    private addressService:AddressService;
    private profileService:ProfileDataService;
    private sellingFeeService:SellingFeeService;

    private router:Router;
    private activatedRoute:ActivatedRoute;
    private formBuilder:FormBuilder;

    product$!:Observable<ProductSummary>;
    lowestAsk$!:Observable<SuccessfullRequest | null>;
    highestBid$!:Observable<SuccessfullRequest | null>;
    addresses$!:Observable<Address[]>;
    bankAccount$!:Observable<BankAccount |null>;
    phoneNumber$!:Observable<PhoneNumber | null>;
    sellingFee$!:Observable<SellingFee>;
    shippingFee$!:Observable<SuccessfullRequest>;

    private destroyStream:Subject<void>=new Subject<void>();

    productId!:string | null;
    size!:string | null;
    
    askForm: FormGroup;
    errorMessage: string | null = null;
    totalPayout: number = 0;
    buttonMessage:string='Place Ask'

    constructor(askService:AskService,bidService:BidService,formBuilder:FormBuilder,productService:ProductDetailsService,router:Router,activatedRoute:ActivatedRoute,addressService:AddressService,profileService:ProfileDataService,sellingFeeService:SellingFeeService){
      this.askService=askService;
      this.bidService=bidService;
      this.productService=productService;
      this.router=router;
      this.activatedRoute=activatedRoute;
      this.addressService=addressService;
      this.profileService=profileService;
      this.sellingFeeService=sellingFeeService;
      this.formBuilder=formBuilder;
      this.askForm = this.formBuilder.group({
        price: ['', [Validators.required, Validators.min(1), Validators.pattern(/^[0-9]*$/)]], // int positive numbers only
        address: ['', Validators.required]
      });
    }

    ngOnInit(): void {
      this.productId=this.activatedRoute.snapshot.paramMap.get('productId');
      this.size=this.activatedRoute.snapshot.paramMap.get('size');
      if(!this.size || !this.productId)
        this.router.navigate(['home']);
      this.getProduct();
      this.getAddresses();
      this.getHighestBid();
      this.getLowestAsk();
      this.getBankAccount();
      this.getPhone();
      this.getShippingFee();
      this.getSellingFee();
      this.askForm.get('price')?.valueChanges.pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe(price => {
        if (price) {
          this.calculateTotalPayout(price);
        }
      });
    }

    getImage(productName:string | undefined){
      return `${productName}.jpg`;
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
      if(this.productId)
        this.product$=this.handleObservable(
          this.productService.loadProductSummary(this.productId)
      );
    }

    private getSellingFee(){
      this.sellingFee$=this.handleObservable(
        this.sellingFeeService.getCurrentSellingFee()
      );
    }

    private getAddresses(){
      this.addresses$=this.handleObservable(
        this.addressService.getUserAddresses()
      );
    }

    private getBankAccount(){
      this.bankAccount$=this.handleObservable(
        this.profileService.getBankAccount()
      );
    }

    private getPhone(){
      this.phoneNumber$=this.handleObservable(
        this.profileService.getPhoneNumber()
      );
    }

    private getLowestAsk(){
      if(this.productId && this.size)
        this.lowestAsk$=this.handleObservable(
          this.askService.findLowestAskByProductIdAndSize(this.productId,this.size)
        );
    }

    private getHighestBid(){
      if(this.productId && this.size)
        this.highestBid$=this.handleObservable(
          this.bidService.findHighestBidByProductIdAndSize(this.productId,this.size)
        );
    }

    private getShippingFee(){
      this.shippingFee$=this.handleObservable(
        this.sellingFeeService.getShippingFee()
      );
    }

    calculateTotalPayoutDisplay(price: number, percentage: number, shipping: string): number {
      const shippingFee = parseFloat(shipping) || 0;
      const sellingFeeAmount = price * (percentage / 100);
      return price - sellingFeeAmount - shippingFee;
    }

    private calculateTotalPayout(price: number): void {
      this.sellingFee$.pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe(fee => {
        this.shippingFee$.pipe(
          takeUntil(
            this.destroyStream
          )
        )
        .subscribe(shipping => {
          const sellingFeeAmount = price * (fee.percentage / 100);
          const shippingFeeAmount = parseInt(shipping.message) || 0;
          this.totalPayout = price - sellingFeeAmount - shippingFeeAmount;
        });
      });
    }
  
    adjustPriceToHighestBid(highestBid: string): void {
      const highestBidValue = Math.round(parseInt(highestBid) || 0);
      const currentPrice = Math.round(this.askForm.get('price')?.value || 0);
      
      if (currentPrice < highestBidValue && highestBidValue > 0) {
        this.askForm.get('price')?.setValue(highestBidValue);
      }
    }

    onSubmit(): void {
      if (this.askForm.invalid || this.totalPayout <=0 ) {
        this.errorMessage = 'Please fill all fields correctly.';
        return;
      }
      this.buttonMessage='Placing Ask...'
      const ask=new Ask();
      this.bankAccount$.pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe({
        next:(data)=>{
          ask.bankAccountId=data?.id ? data.id : '';
          const address=this.askForm.get('address')?.value;
          const amount=this.askForm.get('price')?.value;
          if(this.productId && address && amount && this.size && ask.bankAccountId.trim()!==''){
            ask.addressId=address;
            ask.productId=this.productId;
            ask.amount=amount;
            ask.size=this.size;
            this.postAsk(ask);
          }
          else {
            this.errorMessage='All fields are mandatory.';
            this.buttonMessage='Place Ask';
            throwError(()=> new Error('All fields are mandatory.'));
          }
        },
        error: (error)=> {
          this.errorMessage=error.error?.error
          this.buttonMessage='Place Ask';
        }
      })
    }

    private postAsk(ask:Ask){
      this.askService.saveAsk(ask).pipe(
        takeUntil(
          this.destroyStream
        )
      )
      .subscribe({
        next: (data)=>{
          let id;
          if('reference' in data){
            id=(data as TransactionSuccess).id;
            this.router.navigate(['sales/'+id]);
          }
          else{
            id=(data as AskDetails).id;
            this.router.navigate(['asks/'+id]);
          }
        },
        error: (error)=>{
          this.errorMessage=error.error?.error;
          this.buttonMessage='Place Ask';
        }
      });
    }

    ngOnDestroy() :void{
      this.destroyStream.next();
      this.destroyStream.complete();
    }

}
