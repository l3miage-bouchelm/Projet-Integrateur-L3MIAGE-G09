import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivreurLoginComponent } from './livreur-login.component';

describe('LivreurLoginComponent', () => {
  let component: LivreurLoginComponent;
  let fixture: ComponentFixture<LivreurLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LivreurLoginComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LivreurLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
