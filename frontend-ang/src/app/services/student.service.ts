import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {MatTableDataSource} from "@angular/material/table";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Payment, Student} from "../model/students.model";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) { }

  getAllPayment(): Observable<Array<Payment>>{
    return this.http.get<Array<Payment>>(`${environment.backendHost}/payments`);
  }
  getStudents(): Observable<Array<Student>>{
    return this.http.get<Array<Student>>(`${environment.backendHost}/students`);
  }
  getStudentPayment(code: string): Observable<Array<Payment>>{
    return this.http.get<Array<Payment>>(`${environment.backendHost}/students/${code}/payments`);
  }
  savePayment(formData: any): Observable<Payment>{
    return this.http.post<Payment>(`${environment.backendHost}/payments`, formData);
  }
}
