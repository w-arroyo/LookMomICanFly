import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePhoneNumberComponent } from './profile-phone-number.component';

describe('ProfilePhoneNumberComponent', () => {
  let component: ProfilePhoneNumberComponent;
  let fixture: ComponentFixture<ProfilePhoneNumberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilePhoneNumberComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfilePhoneNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
