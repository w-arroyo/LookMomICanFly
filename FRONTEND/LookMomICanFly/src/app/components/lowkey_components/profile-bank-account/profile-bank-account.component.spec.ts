import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileBankAccountComponent } from './profile-bank-account.component';

describe('ProfileBankAccountComponent', () => {
  let component: ProfileBankAccountComponent;
  let fixture: ComponentFixture<ProfileBankAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileBankAccountComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileBankAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
