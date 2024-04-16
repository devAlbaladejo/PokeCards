import { TestBed } from '@angular/core/testing';

import { UsercardsService } from './usercards.service';

describe('UsercardsService', () => {
  let service: UsercardsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsercardsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
