import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { authGuard } from './services/auth.guard';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
{
  path: '',
  component: HomeComponent,
  pathMatch: 'full'
},
{
  path: 'login',
  component: LoginComponent,
  pathMatch: 'full'
},
{
  path: 'dashboard',
  component: DashboardComponent,
  pathMatch: 'full',
  canActivate: [authGuard]
},
{
  path: 'signup',
  component: SignupComponent,
  pathMatch: 'full',
}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
