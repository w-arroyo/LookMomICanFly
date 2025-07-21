import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ProductSummary } from '../../../models/product_summary.model';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AskService } from '../../../services/ask/ask.service';
import { catchError, Observable, throwError } from 'rxjs';
import { SuccessfullRequest } from '../../../models/successful_request.model';

@Component({
  selector: 'app-product-summary-details',
  imports: [CommonModule],
  templateUrl: './product-summary-details.component.html',
  styleUrl: './product-summary-details.component.css'
})
export class ProductSummaryDetailsComponent implements OnInit{

  private router:Router;
  private askService: AskService;
  askAmount$!: Observable<SuccessfullRequest>;
  @Input() productSummary!: ProductSummary;

  constructor(router:Router, askService: AskService){
    this.router=router;
    this.askService=askService;
  }

  ngOnInit(): void {
    this.askAmount$=this.askService.getLowestAskAmount(this.productSummary.id).pipe(
      catchError(
        (error)=>{
          const message= error.error?.error || 'Server error.';
          return throwError(()=>new Error(message));
        }
      )
    )
  }

  getImage(name: String): string{
    return `${name}.jpg`
  }

}
