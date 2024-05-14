import {Component, OnInit} from '@angular/core';
import {StudentService} from "../services/student.service";
import {Student} from "../model/students.model";
import {MatTableDataSource} from "@angular/material/table";
import {Router} from "@angular/router";

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.css'
})
export class StudentsComponent implements OnInit{
  students!: Array<Student>;
  studentDataSource!: MatTableDataSource<Student>;
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'code', 'programId', 'payments']
  ngOnInit(): void {
    this.studentService.getStudents()
      .subscribe({
        next: students => {
          this.students = students;
          this.studentDataSource = new MatTableDataSource<Student>(this.students);
        },
        error: err => {
          console.log(err)
        }
      })
  }

  constructor(private studentService: StudentService, private router: Router) {
  }


  studentPayments(student: Student){
    this.router.navigateByUrl(`/admin/student-details/${student.code}`);
  }
}
