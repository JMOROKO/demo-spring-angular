import {Component, OnInit} from '@angular/core';
import {StudentService} from "../services/student.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrl: './payment-details.component.css'
})
export class PaymentDetailsComponent implements OnInit{
  paymentId!: number;
  pdfFileURL!: any;

  ngOnInit(): void {
  }

  constructor(private studentService: StudentService, private router: ActivatedRoute) {
    this.paymentId = this.router.snapshot.params['id'];
    let mediaType = 'application/pdf';
    this.studentService.getPaymentDetails(this.paymentId).subscribe({
      next: value => {
        let blob = new Blob([value], {type: mediaType});
        this.pdfFileURL = window.URL.createObjectURL(blob);
      },
      error: err => {
        console.log(err)
      }
    })
  }

  afterLoadComplete($event: any) {

  }
}
