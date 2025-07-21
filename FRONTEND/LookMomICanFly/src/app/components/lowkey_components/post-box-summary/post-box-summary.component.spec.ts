import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostBoxSummaryComponent } from './post-box-summary.component';

describe('PostBoxSummaryComponent', () => {
  let component: PostBoxSummaryComponent;
  let fixture: ComponentFixture<PostBoxSummaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostBoxSummaryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostBoxSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
