import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductSummaryListComponent } from './product-summary-list.component';

describe('ProductSummaryListComponent', () => {
  let component: ProductSummaryListComponent;
  let fixture: ComponentFixture<ProductSummaryListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductSummaryListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductSummaryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
