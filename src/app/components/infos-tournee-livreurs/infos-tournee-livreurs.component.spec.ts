import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfosTourneeLivreursComponent } from './infos-tournee-livreurs.component';

describe('InfosTourneeLivreursComponent', () => {
  let component: InfosTourneeLivreursComponent;
  let fixture: ComponentFixture<InfosTourneeLivreursComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfosTourneeLivreursComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InfosTourneeLivreursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
