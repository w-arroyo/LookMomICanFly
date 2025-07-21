import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileTransactionsComponent } from './profile-transactions.component';

describe('ProfileTransactionsComponent', () => {
  let component: ProfileTransactionsComponent;
  let fixture: ComponentFixture<ProfileTransactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileTransactionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
