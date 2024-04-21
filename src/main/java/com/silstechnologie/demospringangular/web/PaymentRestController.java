package com.silstechnologie.demospringangular.web;

import com.silstechnologie.demospringangular.entities.Payment;
import com.silstechnologie.demospringangular.entities.PaymentStatus;
import com.silstechnologie.demospringangular.entities.PaymentType;
import com.silstechnologie.demospringangular.entities.Student;
import com.silstechnologie.demospringangular.repository.PaymentRepository;
import com.silstechnologie.demospringangular.repository.StudentRepository;
import com.silstechnologie.demospringangular.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;
    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }
    @GetMapping(path = "/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }
    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }
    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @PostMapping(path="/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate datePayment, double amount, PaymentType type, String studentCode)
            throws IOException {
        return this.paymentService.savePayment(file, datePayment, amount, type, studentCode);
    }

    // POUR définir plusieurs type de fichier retourné il faut produces = {MediaType.APPLICATION_PDF_VALUE, autre type}
    @GetMapping(path = "/payementFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        return this.getPaymentFile(paymentId);
    }
}
// 1- quel type d'architecture logiciel avons-nous utilisé ?
    // => Architecture monolytique multicouche
// 2- es ce qu'on aura l'occasion d'utiliser des interfaces dans un projet que nous allons implémenté ?
// 3- es ce qu'on aura un autre TP pour l'utilisation complète des DTO ?

// 4- Est-il possible de mettre le fichier uploadé dans un dossier du projet et enregistrer dans la base de données un lien relatif ?
 //=> Ce n'est pas une bonne pratique de stoquer les fichiers dans un dossier de l'application puisqu'il va être un fichier jar à la fin