package com.silstechnologie.demospringangular.repository;

import com.silstechnologie.demospringangular.entities.Payment;
import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
