import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {ProfileComponent} from "./profile/profile.component";
import {LoginComponent} from "./login/login.component";
import {LoadStudentsComponent} from "./load-students/load-students.component";
import {LoadPaymentsComponent} from "./load-payments/load-payments.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {StudentsComponent} from "./students/students.component";
import {PaymentsComponent} from "./payments/payments.component";
import {AdminTemplateComponent} from "./admin-template/admin-template.component";
import {AuthGuard} from "./guards/auth.guard";
import {AuthorisationGuard} from "./guards/authorisation.guard";
import {StudentDetailsComponent} from "./student-details/student-details.component";
import {NewPaymentComponent} from "./new-payment/new-payment.component";

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "login", component: LoginComponent},
  {path: "admin", component: AdminTemplateComponent,
    canActivate: [AuthGuard],
    children: [
      {path: "loadStudents", component: LoadStudentsComponent, canActivate: [AuthorisationGuard], data: {roles: ['ADMIN']}},
      {path: "loadPayments", component: LoadPaymentsComponent, canActivate: [AuthorisationGuard], data: {roles: ['ADMIN']}},
      {path: "student-details/:code", component: StudentDetailsComponent, canActivate: [AuthorisationGuard], data: {roles: ['ADMIN']}},
      {path: "new-payment/:code", component: NewPaymentComponent, canActivate: [AuthorisationGuard], data: {roles: ['ADMIN']}},
      {path: "home", component: HomeComponent},
      {path: "profile", component: ProfileComponent},
      {path: "dashboard", component: DashboardComponent},
      {path: "students", component: StudentsComponent},
      {path: "payments", component: PaymentsComponent},
    ]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
