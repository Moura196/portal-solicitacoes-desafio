import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponente } from './navbar';

describe('NavbarComponente', () => {
  let component: NavbarComponente;
  let fixture: ComponentFixture<NavbarComponente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarComponente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarComponente);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
