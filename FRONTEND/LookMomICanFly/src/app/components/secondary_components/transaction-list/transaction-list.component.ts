import { CommonModule } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { TransactionBoxSummaryComponent } from '../../lowkey_components/transaction-box-summary/transaction-box-summary.component';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { SaleService } from '../../../services/sale/sale.service';
import { OrderService } from '../../../services/order/order.service';
import { catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { TransactionSummary } from '../../../models/transaction_summary.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-transaction-list',
  imports: [CommonModule, TransactionBoxSummaryComponent],
  templateUrl: './transaction-list.component.html',
  styleUrl: './transaction-list.component.css'
})
export class TransactionListComponent implements OnInit, OnDestroy{

  private transactionService:TransactionService;
  private saleService:SaleService;
  private orderService:OrderService;

  transactions$!:Observable<TransactionSummary[]>;
  private destroyStream:Subject<void>=new Subject<void>();

  type:string = 'pending'

  sectionParam:string='';
  private router:Router;
  private activatedRoute:ActivatedRoute;
  
  constructor(transactionService:TransactionService,saleService:SaleService,orderService:OrderService,router:Router,activatedRoute:ActivatedRoute){
    this.transactionService=transactionService;
    this.saleService=saleService;
    this.orderService=orderService;
    this.router=router;
    this.activatedRoute=activatedRoute;  
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.pipe(
      takeUntil(
        this.destroyStream
      )
    )
    .subscribe({
      next: (data)=>{
        this.sectionParam=data.get('section') || '';
        this.getTransactions();
      },
      error: (error)=> this.router.navigate(['account/profile/my-profile'])
    });
    
  }

  getTransactions(){
    if(this.sectionParam==='selling')
      this.getSales();
    else if(this.sectionParam==='buying')
      this.getOrders();
    else this.router.navigate(['account/profile/my-profile']);
  }

  private getSales(){
    this.transactions$=this.handleObservable(
      this.saleService.findAll()
    );
  }

  private getOrders(){
    this.transactions$=this.handleObservable(
      this.orderService.findAll()
    )
  }

  private handleObservable(observable:Observable<TransactionSummary[]>){
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

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
