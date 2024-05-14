package com.silstechnologie.demospringangular.services;

import com.silstechnologie.demospringangular.dtos.NewPaymentDto;
import com.silstechnologie.demospringangular.entities.Payment;
import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import com.silstechnologie.demospringangular.entities.Student;
import com.silstechnologie.demospringangular.repository.PaymentRepository;
import com.silstechnologie.demospringangular.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(@RequestParam MultipartFile file, NewPaymentDto newPaymentDto)
            throws IOException {


        //identification du dossier de sauvegarde des fichiers "enset-data/payments"
        //c'est ça la bonne pratique
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "payments");

        //creation du dossier s'il n'existe pas
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        //création du nom du fichier unique du fichier
        String fileName = UUID.randomUUID().toString();

        //sauvegarde du fichier
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "payments", fileName+".pdf");

        //transfert du fichier vers le dossier
        Files.copy(file.getInputStream(), filePath);

        //recherche et création de l'objet student
        Student student = studentRepository.findByCode(newPaymentDto.getStudentCode());

        //creation de l'objet payment
        Payment payment = Payment.builder()
                .datePaiement(newPaymentDto.getDatePaiement())
                .type(newPaymentDto.getType())
                .student(student)
                .amount(newPaymentDto.getAmount())
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();

        //enregistrement des données
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();

        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
