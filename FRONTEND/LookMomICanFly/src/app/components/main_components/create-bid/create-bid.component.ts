import { Component, OnDestroy, OnInit } from '@angular/core';
import { Bid } from '../../../models/bid.model';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { BidService } from '../../../services/bid/bid.service';
import { ProductDetailsService } from '../../../services/product_details/product-details.service';
import { AddressService } from '../../../services/address/address.service';
import { ProductSummary } from '../../../models/product_summary.model';
import { SuccessfullRequest } from '../../../models/successful_request.model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Address } from '../../../models/address,model';
import { ShippingOption } from '../../../models/shipping_option.model';
import { ShippingOptionService } from '../../../services/shipping_option/shipping-option.service';
import { AskService } from '../../../services/ask/ask.service';
import { PaymentService } from '../../../services/payment/payment.service';

@Component({
  selector: 'app-create-bid',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './create-bid.component.html',
  styleUrl: './create-bid.component.css'
})
export class CreateBidComponent implements OnInit, OnDestroy{
  private bidService: BidService;
  private askService:AskService;
  private productService: ProductDetailsService;
  private addressService: AddressService;
  private shippingOptionService: ShippingOptionService;
  private paymentService:PaymentService;

  private router: Router;
  private activatedRoute: ActivatedRoute;
  private formBuilder: FormBuilder;

  product$!: Observable<ProductSummary>;
  lowestAsk$!: Observable<SuccessfullRequest | null>;
  highestBid$!: Observable<SuccessfullRequest | null>;
  addresses$!: Observable<Address[]>;
  shippingOptions$!: Observable<ShippingOption[]>;
  operationalFee$!: Observable<SuccessfullRequest>;

  private destroyStream: Subject<void> = new Subject<void>();

  productId!: string | null;
  size!: string | null;
  
  bidForm: FormGroup;
  errorMessage: string | null = null;
  totalAmount: number = 0;

  constructor(bidService: BidService,askService:AskService,paymentService:PaymentService,formBuilder: FormBuilder,productService: ProductDetailsService,router: Router,activatedRoute: ActivatedRoute,addressService: AddressService,shippingOptionService: ShippingOptionService){
    this.bidService = bidService;
    this.productService = productService;
    this.router = router;
    this.activatedRoute = activatedRoute;
    this.addressService = addressService;
    this.askService=askService;
    this.shippingOptionService = shippingOptionService;
    this.formBuilder = formBuilder;
    this.paymentService=paymentService;
    this.bidForm = this.formBuilder.group({
      amount: ['', [Validators.required, Validators.min(1), Validators.pattern(/^[0-9]*$/)]], // int positive numbers only
      address: ['', Validators.required],
      shippingOption: ['', Validators.required],
      shippingPrice: [0, Validators.required]
    });
  }

  ngOnInit(): void {
    this.productId = this.activatedRoute.snapshot.paramMap.get('productId');
    this.size = this.activatedRoute.snapshot.paramMap.get('size');
    if(!this.size || !this.productId) {
      this.router.navigate(['home']);
      return;
    }
    this.getProduct();
    this.getAddresses();
    this.getShippingOptions();
    this.getHighestBid();
    this.getLowestAsk();
    this.getOperationalFee();

    this.bidForm.get('amount')?.valueChanges.pipe(
      takeUntil(this.destroyStream)
    ).subscribe(amount => {
      if(amount){
        this.calculateTotalAmount();
      }
    });

    this.bidForm.get('shippingOption')?.valueChanges.pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe(
      (shippingId)=>{
        if(shippingId){
          this.calculateTotalAmount();
        }
      }
    )

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

  private getAddresses(){
    this.addresses$=this.handleObservable(
      this.addressService.getUserAddresses()
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

  private getShippingOptions() {
    this.shippingOptions$ = this.handleObservable(
      this.shippingOptionService.getShippingOptions()
    );
  }

  private getOperationalFee() {
    this.operationalFee$ = this.handleObservable(
      this.bidService.getOperationalFee()
    );
  }

  calculateTotalAmount(): void {
    this.operationalFee$.pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe(fee => {
      const operationalFee = parseFloat(fee.message) || 0;
      const amount=this.bidForm.get('amount')?.value|| 0;
      const shippingPrice = this.bidForm.get('shippingPrice')?.value || 0;
      this.totalAmount = +amount + operationalFee + +shippingPrice;
    });
  }

  updateShippingPrice(options: ShippingOption[] | undefined) {
    const selectedId = this.bidForm.get('shippingOption')?.value;
    const selectedOption = options?.find(opt => opt.id === selectedId);
    this.bidForm.get('shippingPrice')?.setValue(selectedOption?.price || 0);
  }

  adjustAmountToLowestAsk(lowestAsk: string): void {
    const lowestAskValue = Math.round(parseInt(lowestAsk) || 0);
    const currentAmount = Math.round(this.bidForm.get('amount')?.value || 0);   
    if(currentAmount > lowestAskValue && lowestAskValue > 0) {
      this.bidForm.get('amount')?.setValue(lowestAskValue);
    }
  }

  onSubmit(): void {
    if(this.bidForm.invalid) {
      this.errorMessage = 'Please fill all fields correctly.';
      return;
    }
    const bid = new Bid();
    const address = this.bidForm.get('address')?.value;
    const shippingOption = this.bidForm.get('shippingOption')?.value;
    const amount = this.bidForm.get('amount')?.value;
    if(this.productId && address && shippingOption && amount && this.size) {
      bid.addressId = address;
      bid.shippingOptionId = shippingOption;
      bid.productId = this.productId;
      bid.amount = amount;
      bid.size = this.size;
      this.goToPayment(bid);
    }

  }

  private goToPayment(bid:Bid){
    this.paymentService.bid=bid;
    this.paymentService.totalAmount=this.totalAmount;
    this.router.navigate(['payment'])
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
