import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterStateSnapshot} from "@angular/router";
import {StudentService} from "../services/student.service";
import {Payment} from "../model/students.model";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.css'
})
export class StudentDetailsComponent implements OnInit{
  studentCode!: string;
  studentPayment!: Array<Payment>;
  paymentsDataSource!: MatTableDataSource<Payment>;
  public displayedColumns: string[] = ['id', 'datePaiement', 'amount', 'type', 'status', 'firstName'];
  ngOnInit(): void {
    this.studentCode = this.activedRoute.snapshot.params['code'];
    this.studentService.getStudentPayment(this.studentCode).subscribe({
      next: value => {
        this.studentPayment = value;
        this.paymentsDataSource = new MatTableDataSource<Payment>(this.studentPayment);
      },
      error: err =>{
        console.log(err);
      }
    })
  }

  constructor(private activedRoute: ActivatedRoute, private studentService: StudentService, private router: Router) {
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.paymentsDataSource.filter = filterValue.trim().toLowerCase();
  }

  newPayment() {
    this.router.navigateByUrl(`/admin/new-payment/${this.studentCode}`);
  }
}
