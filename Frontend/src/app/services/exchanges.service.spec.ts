import { TestBed } from '@angular/core/testing';

import { ExchangesService } from './exchanges.service';

describe('ExchangesService', () => {
  let service: ExchangesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExchangesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
