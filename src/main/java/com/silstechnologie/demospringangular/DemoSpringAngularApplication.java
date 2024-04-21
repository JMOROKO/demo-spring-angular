package com.silstechnologie.demospringangular;

import com.silstechnologie.demospringangular.entities.Payment;
import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import com.silstechnologie.demospringangular.entities.Student;
import com.silstechnologie.demospringangular.repository.PaymentRepository;
import com.silstechnologie.demospringangular.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoSpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringAngularApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder()
                            .code("1225788")
                            .firstName("Moroko")
                            .lastName("Jean-romaric")
                            .programId("SDIA")
                            .id(UUID.randomUUID().toString())
                    .build());
            studentRepository.save(Student.builder()
                            .code("1275788")
                            .firstName("Moroko")
                            .lastName("Lega jean-renaud")
                            .programId("SDIA")
                            .id(UUID.randomUUID().toString())
                    .build());
            studentRepository.save(Student.builder()
                            .code("17881788")
                            .firstName("Moroko")
                            .lastName("Franck morel")
                            .programId("SDIA")
                            .id(UUID.randomUUID().toString())
                    .build());
            studentRepository.save(Student.builder()
                            .code("2425788")
                            .firstName("Moroko")
                            .lastName("Inès")
                            .programId("SDIA")
                            .id(UUID.randomUUID().toString())
                    .build());
            studentRepository.save(Student.builder()
                            .code("4225788")
                            .firstName("Moroko")
                            .lastName("Christelle")
                            .programId("SDIA")
                            .id(UUID.randomUUID().toString())
                    .build());

            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st -> {
                for(int i = 0; i< 10; i++){
                    int index = random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder()
                            .amount(1000+(int) (Math.random()*2000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .datePaiement(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }
}
