import { TestBed } from '@angular/core/testing';

import { NoGuardService } from './no-guard.service';

describe('NoGuardService', () => {
  let service: NoGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NoGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
