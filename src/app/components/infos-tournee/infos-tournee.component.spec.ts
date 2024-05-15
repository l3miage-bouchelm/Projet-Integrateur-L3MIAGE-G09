import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfosTourneeComponent } from './infos-tournee.component';

describe('InfosTourneeComponent', () => {
  let component: InfosTourneeComponent;
  let fixture: ComponentFixture<InfosTourneeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfosTourneeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InfosTourneeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
