package com.silstechnologie.demospringangular.dtos;

import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import com.silstechnologie.demospringangular.entities.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDto {
    private Long id;
    private LocalDate datePaiement;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
}
