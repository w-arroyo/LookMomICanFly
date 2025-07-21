import { Component, OnDestroy, OnInit } from '@angular/core';
import { TransactionListComponent } from '../../secondary_components/transaction-list/transaction-list.component';
import { PostListComponent } from '../../secondary_components/post-list/post-list.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { PendingTransactionListComponent } from '../../secondary_components/pending-transaction-list/pending-transaction-list.component';
import { CompletedTransactionListComponent } from '../../secondary_components/completed-transaction-list/completed-transaction-list.component';

@Component({
  selector: 'app-profile-transactions',
  imports: [PendingTransactionListComponent, CompletedTransactionListComponent, PostListComponent, CommonModule],
  templateUrl: './profile-transactions.component.html',
  styleUrl: './profile-transactions.component.css'
})
export class ProfileTransactionsComponent implements OnInit,OnDestroy{

  private activatedRoute:ActivatedRoute;
  private router:Router;
  private destroyStream:Subject<void>=new Subject<void>();
  activeTab:string = 'active';
  section:string='';

  constructor(activatedRoute:ActivatedRoute,router:Router){
    this.activatedRoute=activatedRoute;
    this.router=router;
        activatedRoute.paramMap.pipe(
              takeUntil(
                this.destroyStream
              )
            )
            .subscribe({
              next: (data)=>{
                this.section=data.get('section') || '';
              },
              error: (error)=> this.router.navigate(['account/profile/my-profile'])
            });
        if(this.section==='')
          this.router.navigate(['account/profile/my-profile']);
  }

  ngOnInit(): void {
    
  }

  ngOnDestroy(): void {
    this.destroyStream.next();
    this.destroyStream.complete();
  }

}
