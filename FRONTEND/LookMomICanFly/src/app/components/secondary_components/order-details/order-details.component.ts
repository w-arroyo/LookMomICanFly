import { Component, OnDestroy, OnInit } from '@angular/core';
import { Order } from '../../../models/order.model';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { OrderService } from '../../../services/order/order.service';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-order-details',
  imports: [CommonModule,RouterLinkWithHref],
  templateUrl: './order-details.component.html',
  styleUrl: './order-details.component.css'
})
export class OrderDetailsComponent implements OnInit,OnDestroy{

  orderId:string='';
  order$!:Observable<Order>;

  private orderService:OrderService;
  private router:Router;
  private destroyStream:Subject<void>=new Subject<void>();
  private activatedRoute:ActivatedRoute;

  constructor(orderService:OrderService,router:Router,activatedRoute:ActivatedRoute) {
    this.orderService=orderService;
    this.router=router;
    this.activatedRoute=activatedRoute;
  }

  ngOnInit():void {
    const orderId=this.activatedRoute.snapshot.paramMap.get('orderId');
    if(!orderId){
      this.router.navigate(['home']);
      return;
    }
    this.orderId=orderId;
    this.getOrder();
  }

  private getOrder() {
    this.order$ = this.orderService.findOrder(this.orderId).pipe(
      takeUntil(
        this.destroyStream
      ),
      catchError(
        (error) => {
        console.log(error);
        this.router.navigate(['home']);
        return throwError(() => new Error(error.error?.error));
      })
    );
  }

  getProductImage(productName:string):string {
    return `${productName}.jpg`;
  }

  calculateTotalCost(amount:number,feePercentage:number,shippingCost: number):number {
    return amount+feePercentage+shippingCost;
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }
}
