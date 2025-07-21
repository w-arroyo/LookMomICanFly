import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { AskDetails } from '../../../models/full_ask.model';
import { AskService } from '../../../services/ask/ask.service';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'app-ask-details',
  imports: [CommonModule,RouterLinkWithHref],
  templateUrl: './ask-details.component.html',
  styleUrl: './ask-details.component.css'
})
export class AskDetailsComponent implements OnInit,OnDestroy{

  askId:string='';
  ask$!:Observable<AskDetails>;
  private askService:AskService;
  private router:Router;
  private activatedRoute:ActivatedRoute;
  private destroyStream:Subject<void>=new Subject<void>();

  constructor(askService:AskService,router:Router,activatedRoute:ActivatedRoute){
    this.askService=askService;
    this.router=router;
    this.activatedRoute=activatedRoute;
  }

  ngOnInit(): void {
    const ask=this.activatedRoute.snapshot.paramMap.get('askId');
    if(!ask){
      this.router.navigate(['home']);
      return;
    }
    this.askId=ask;
    this.getAsk();
  }

  private getAsk(){
    this.ask$=this.askService.findAsk(this.askId).pipe(
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

  getProductImage(productName: string): string {
    return `${productName}.jpg`;
  }

  calculateTotalPayout(amount: number, feePercentage: number, shippingFee: number): number {
    const feeAmount = amount * (feePercentage / 100);
    return amount - feeAmount - shippingFee;
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
