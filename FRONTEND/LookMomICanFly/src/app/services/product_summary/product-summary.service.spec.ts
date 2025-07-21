import { TestBed } from '@angular/core/testing';

import { ProductSummaryService } from './product-summary.service';

describe('ProductSummaryService', () => {
  let service: ProductSummaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductSummaryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
