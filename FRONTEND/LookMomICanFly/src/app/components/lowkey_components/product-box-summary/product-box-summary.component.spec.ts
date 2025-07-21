import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductBoxSummaryComponent } from './product-box-summary.component';

describe('ProductBoxSummaryComponent', () => {
  let component: ProductBoxSummaryComponent;
  let fixture: ComponentFixture<ProductBoxSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductBoxSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductBoxSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
