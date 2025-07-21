import { Component, Input } from '@angular/core';
import { ProductSummary } from '../../../models/product_summary.model';

@Component({
  selector: 'app-product-box-summary',
  imports: [],
  templateUrl: './product-box-summary.component.html',
  styleUrl: './product-box-summary.component.css'
})
export class ProductBoxSummaryComponent {

  @Input() product!:ProductSummary;

  getProductImage(){
    return `${this.product.name}.jpg`
  }

}
