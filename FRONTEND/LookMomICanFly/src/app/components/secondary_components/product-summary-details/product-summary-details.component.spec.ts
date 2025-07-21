import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductSummaryDetailsComponent } from './product-summary-details.component';

describe('ProductSummaryDetailsComponent', () => {
  let component: ProductSummaryDetailsComponent;
  let fixture: ComponentFixture<ProductSummaryDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductSummaryDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductSummaryDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
