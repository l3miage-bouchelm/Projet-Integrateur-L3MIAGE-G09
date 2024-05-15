import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivreurTravailComponent } from './livreur-travail.component';

describe('LivreurTravailComponent', () => {
  let component: LivreurTravailComponent;
  let fixture: ComponentFixture<LivreurTravailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LivreurTravailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LivreurTravailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
