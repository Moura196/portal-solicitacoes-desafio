import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponente {

  protected novaSolicitacao(): void {
    // TODO: Criar funcionalidade depois de criar o componente necessário
    console.log('Ação: Nova Solicitação clicada');
  }

  protected pesquisar(): void {
    // TODO: Criar funcionalidade depois de criar o componente necessário
    console.log('Ação: Pesquisar clicado');
  }

  protected sair(): void {
    // TODO: Criar funcionalidade depois de criar o componente necessário
    console.log('Ação: Sair clicado');
  }

}
