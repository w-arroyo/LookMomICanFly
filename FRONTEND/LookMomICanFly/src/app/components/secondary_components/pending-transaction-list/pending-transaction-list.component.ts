import { Component } from '@angular/core';
import { TransactionSummary } from '../../../models/transaction_summary.model';
import { CommonModule } from '@angular/common';
import { SaleService } from '../../../services/sale/sale.service';
import { OrderService } from '../../../services/order/order.service';
import { BehaviorSubject, catchError, Observable, Subject, takeUntil, throwError } from 'rxjs';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionBoxSummaryComponent } from '../../lowkey_components/transaction-box-summary/transaction-box-summary.component';
import { LoadingScreenComponent } from '../../lowkey_components/loading-screen/loading-screen.component';

@Component({
  selector: 'app-pending-transaction-list',
  imports: [CommonModule,TransactionBoxSummaryComponent,LoadingScreenComponent],
  templateUrl: './pending-transaction-list.component.html',
  styleUrl: './pending-transaction-list.component.css'
})
export class PendingTransactionListComponent {

  private transactionService:TransactionService;
  private saleService:SaleService;
  private orderService:OrderService;

  transactions$!:Observable<TransactionSummary[]>;
  private destroyStream:Subject<void>=new Subject<void>();
  loadingBehaviour:BehaviorSubject<boolean>=new BehaviorSubject<boolean>(true);
  loading$=this.loadingBehaviour.asObservable();

  sectionParam:string='';
  private router:Router;
  private activatedRoute:ActivatedRoute;

  statusMessage:string = '';
  messageType:string='success';
  
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
    this.loadingBehaviour.next(false);
  }

  private getOrders(){
    this.transactions$=this.handleObservable(
      this.orderService.findAll()
    );
    this.loadingBehaviour.next(false);
  }

  private handleObservable(observable:Observable<any>){
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

  onTrackingCreated(saleId:string){
    this.loadingBehaviour.next(true);
      this.saleService.newTrackingNumber(saleId)
    .pipe(
      takeUntil(
        this.destroyStream
      )
    ).subscribe({
      next: (data)=>{
        this.loadingBehaviour.next(false);
        this.messageType='success';
        this.statusMessage='New tracking label created. Check your email.';
        this.hideMessage();
      },
      error: (error)=>{
        this.loadingBehaviour.next(false);
        this.messageType='error';
        this.statusMessage=error.error?.error;
        this.hideMessage();
      }
    });
  }

  hideMessage(){
    setTimeout(() => {
      this.statusMessage = '';
    }, 3000);
  }

}
