import { TestBed, inject } from '@angular/core/testing';

import { ApartmentService } from './apartment.service';

describe('ApartmentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApartmentService]
    });
  });

  it('should be created', inject([ApartmentService], (service: ApartmentService) => {
    expect(service).toBeTruthy();
  }));
});
