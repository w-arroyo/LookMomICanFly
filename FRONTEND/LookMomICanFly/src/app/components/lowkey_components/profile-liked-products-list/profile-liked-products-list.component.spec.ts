import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLikedProductsListComponent } from './profile-liked-products-list.component';

describe('ProfileLikedProductsListComponent', () => {
  let component: ProfileLikedProductsListComponent;
  let fixture: ComponentFixture<ProfileLikedProductsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileLikedProductsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileLikedProductsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
