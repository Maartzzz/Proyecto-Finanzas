import { Routes } from '@angular/router';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { RoleGuard } from './guards/role.guard';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { CategoriaComponent } from './pages/categorias/categoria/categoria.component';

export const routes: Routes = [{
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
},
{
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
},
{
    path: '',
    component: LayoutComponent,
    children: [
    {
        path: '',
        redirectTo: 'usuarios',
        pathMatch: 'full'
    },
    {
        path: 'usuarios',
        loadComponent: () => import('./pages/usuarios/usuarios.component').then(m => m.UsuariosComponent),
        canActivate: [RoleGuard]
    },
    {
        path: '',
        redirectTo: 'perfil',
        pathMatch: 'full'
    },
    {
        path: 'perfil',
        loadComponent: () => import('./pages/perfil/perfil.component').then(m => m.PerfilComponent)
    },
    {
        path: '',
        redirectTo: 'categoria',
        pathMatch: 'full'
    },
    {
        path: 'categoria',
        loadComponent: () => import('./pages/categorias/categoria/categoria.component').then(m => m.CategoriaComponent)
    },
    {
        path: '',
        redirectTo: 'movimientos',
        pathMatch: 'full'
    },
    {
        path: 'movimientos',
        loadComponent: () => import('./pages/movimientos/movimientos/movimientos.component').then(m => m.MovimientoComponent)
    },
    {
        path: '',
        redirectTo: 'metas',
        pathMatch: 'full'
    },
    {
        path: 'metas',
        loadComponent: () => import('./pages/meta/meta/meta.component').then(m => m.MetaComponent)
    }
    ]
},
{
    path: '**',
    redirectTo: 'perfil'
}
];

