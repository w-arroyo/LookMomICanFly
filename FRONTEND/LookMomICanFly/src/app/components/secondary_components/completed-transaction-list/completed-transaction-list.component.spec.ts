import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedTransactionListComponent } from './completed-transaction-list.component';

describe('CompletedTransactionListComponent', () => {
  let component: CompletedTransactionListComponent;
  let fixture: ComponentFixture<CompletedTransactionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompletedTransactionListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompletedTransactionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
