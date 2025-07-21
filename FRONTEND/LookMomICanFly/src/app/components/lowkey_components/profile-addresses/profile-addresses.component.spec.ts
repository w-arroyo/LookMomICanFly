import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileAddressesComponent } from './profile-addresses.component';

describe('ProfileAddressesComponent', () => {
  let component: ProfileAddressesComponent;
  let fixture: ComponentFixture<ProfileAddressesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileAddressesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileAddressesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
