import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingTransactionListComponent } from './pending-transaction-list.component';

describe('PendingTransactionListComponent', () => {
  let component: PendingTransactionListComponent;
  let fixture: ComponentFixture<PendingTransactionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PendingTransactionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PendingTransactionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
