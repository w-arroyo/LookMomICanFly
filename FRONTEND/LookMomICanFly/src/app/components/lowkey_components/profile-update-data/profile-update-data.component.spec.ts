import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileUpdateDataComponent } from './profile-update-data.component';

describe('ProfileUpdateDataComponent', () => {
  let component: ProfileUpdateDataComponent;
  let fixture: ComponentFixture<ProfileUpdateDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileUpdateDataComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileUpdateDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
