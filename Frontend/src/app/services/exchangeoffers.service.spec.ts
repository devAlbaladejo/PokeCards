import { TestBed } from '@angular/core/testing';

import { ExchangeoffersService } from './exchangeoffers.service';

describe('ExchangeoffersService', () => {
  let service: ExchangeoffersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExchangeoffersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
