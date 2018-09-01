import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RestaurantsComponent } from './components/restaurants/restaurants.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RestaurantDetailComponent } from './components/restaurant-detail/restaurant-detail.component';
import { UsersComponent } from './components/users/users.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { LoginComponent } from './components/login/login.component';

import { NotFoundComponent } from './components/errors/not-found/not-found.component';
import { AccessForbiddenComponent } from './components/errors/access-forbidden/access-forbidden.component';
import { ServerErrorComponent } from './components/errors/server-error/server-error.component';

import {AuthGuard} from './guard/auth.guard';

const routes: Routes = [
  {
    path: '500',
    component: ServerErrorComponent
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '403',
    component: AccessForbiddenComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'restaurants',
    component: RestaurantsComponent
  },
  {
    path: 'detail/:id',
    component: RestaurantDetailComponent
  },
  {
    path: 'create',
    component: RestaurantDetailComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN', 'REALTOR'] }
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'users',
    component: UsersComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'user-detail/:id',
    component: UserDetailComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'new-user',
    component: UserDetailComponent,
    canActivate: [AuthGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
