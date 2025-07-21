import { TestBed } from '@angular/core/testing';

import { SellingFeeService } from './selling-fee.service';

describe('SellingFeeService', () => {
  let service: SellingFeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SellingFeeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
