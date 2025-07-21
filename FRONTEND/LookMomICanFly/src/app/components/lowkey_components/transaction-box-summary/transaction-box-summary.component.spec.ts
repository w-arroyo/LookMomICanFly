import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionBoxSummaryComponent } from './transaction-box-summary.component';

describe('TransactionBoxSummaryComponent', () => {
  let component: TransactionBoxSummaryComponent;
  let fixture: ComponentFixture<TransactionBoxSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionBoxSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionBoxSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
