import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {PaymentType} from "../model/students.model";
import {StudentService} from "../services/student.service";

@Component({
  selector: 'app-new-payment',
  templateUrl: './new-payment.component.html',
  styleUrl: './new-payment.component.css'
})
export class NewPaymentComponent implements OnInit{
  paymentFormGroup!: FormGroup;
  studentCode!: string;
  paymentTypes: string[] = [];
  pdfFileURL!: string;

  ngOnInit(): void {
    for(let element in PaymentType){
      let value = PaymentType[element];
      if(typeof value === 'string'){
        console.log(value)
        this.paymentTypes.push(value);
      }
    }
    this.studentCode = this.activatedRoute.snapshot.params['code'];
    this.paymentFormGroup = this.fb.group({
      date: this.fb.control(''),
      amount: this.fb.control(''),
      type: this.fb.control(''),
      studentCode: this.fb.control(this.studentCode),
      fileSource: this.fb.control(''),
      fileName: this.fb.control(''),
    }); //1:04:00
  }

  constructor(private fb: FormBuilder, private activatedRoute: ActivatedRoute, private studentService: StudentService) {
  }


  selectFile(event: any) {
    if(event.target.files.length>0){
      let file = event.target.files[0];
      this.paymentFormGroup.patchValue({
        fileSource: file,
        fileName: file.name
      });
      this.pdfFileURL = window.URL.createObjectURL(file);
    }
  }

  savePayment() {
    let date = new Date(this.paymentFormGroup.value.date);
    let formatedDate = date.getDate()+'/'+(date.getMonth()+1)+'/'+date.getFullYear();
    let formData = new FormData();
    formData.set('date', formatedDate);
    formData.set('amount', this.paymentFormGroup.value.amount);
    formData.set('type', this.paymentFormGroup.value.type);
    formData.set('studentCode', this.paymentFormGroup.value.studentCode);
    formData.set('file', this.paymentFormGroup.value.fileSource);

    this.studentService.savePayment(formData)
      .subscribe({
        next: value => {
          alert("Saved successfully")
        },
        error: err => {
          console.log(err)
        }
      })
  }
}
