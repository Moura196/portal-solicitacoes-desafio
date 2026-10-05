import { Routes } from '@angular/router';
import { HomeComponente } from './features/home/home';
import { LoginComponent } from './features/login/login';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        title: 'Login - Portal de Solicitações'
    },
    {
        path: '',
        component: HomeComponente,
        title: 'Dashboard - Portal de Solicitações',
        canActivate: [authGuard]
    },
    {
        path: '**',
        redirectTo: ''
    }
];
