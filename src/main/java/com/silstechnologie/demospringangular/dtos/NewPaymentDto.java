package com.silstechnologie.demospringangular.dtos;

import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class NewPaymentDto {
    private Long id;
    private LocalDate datePaiement;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String studentCode;
}
