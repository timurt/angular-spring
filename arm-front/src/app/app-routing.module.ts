import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApartmentsComponent } from './components/apartments/apartments.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ApartmentDetailComponent } from './components/apartment-detail/apartment-detail.component';
import { UsersComponent } from './components/users/users.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';

const routes: Routes = [
  {
    path: 'apartments',
    component: ApartmentsComponent
  },
  {
    path: 'detail/:id',
    component: ApartmentDetailComponent
  },
  {
    path: 'create',
    component: ApartmentDetailComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'users',
    component: UsersComponent
  },
  {
    path: 'user-detail/:id',
    component: UserDetailComponent
  },
  {
    path: 'new-user',
    component: UserDetailComponent
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
