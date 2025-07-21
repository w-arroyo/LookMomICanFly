import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductBoxSummaryComponent } from '../product-box-summary/product-box-summary.component';
import { TransactionSummary } from '../../../models/transaction_summary.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transaction-box-summary',
  imports: [CommonModule,ProductBoxSummaryComponent],
  templateUrl: './transaction-box-summary.component.html',
  styleUrl: './transaction-box-summary.component.css'
})
export class TransactionBoxSummaryComponent {

  @Input() transaction!: TransactionSummary;
  @Input() sectionParam!:string;
  @Output() createTrackingNumber=new EventEmitter<string>();
  private router:Router;

  errorMessage:string | null=null;

  constructor(router:Router){
    this.router=router;
  }

  createTracking(){
    this.createTrackingNumber.emit(this.transaction.id);
  }

  goToProduct(){
    this.router.navigate([`/products/${this.transaction.product.category.toLowerCase()}/details/${this.transaction.product.id}`]);
  }

  goToDetails(){
    if(this.sectionParam==='selling')
      this.router.navigate(['sales/'+this.transaction.id]);
    else if(this,this.sectionParam==='buying')
      this.router.navigate(['orders/'+this.transaction.id]);
  }

}
