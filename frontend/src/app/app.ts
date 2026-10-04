import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponente } from './core/layout/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    NavbarComponente
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
