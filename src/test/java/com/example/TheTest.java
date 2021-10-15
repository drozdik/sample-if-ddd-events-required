package com.example;

import com.example.invoice.Invoice;
import com.example.invoice.InvoiceId;
import com.example.invoice.InvoiceRepo;
import com.example.payment.PaymentRegisterService;
import com.example.visit.Visit;
import com.example.visit.VisitRepo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("unit")
public class TheTest {

    private final InvoiceRepo invoiceRepo = new InvoiceRepo();
    private final VisitRepo visitRepo = new VisitRepo();
    private final PaymentRegisterService paymentRegisterService = new PaymentRegisterService(invoiceRepo, visitRepo);

    @Test
    void visitShouldBecomePaidWhenInvoicePaid() {
        // visit
        Visit visitA = new Visit();
        visitA = visitRepo.save(visitA);
        assertThat(visitA.isUnpaid()).isTrue();
        // invoice
        Invoice invoiceA = new Invoice(150, visitA.getId());
        invoiceA = invoiceRepo.save(invoiceA);
        paymentRegisterService.register(150, invoiceA.getId());

        assertThat(visitA.isPaid()).isTrue();
    }

    @Test
    void onlyFullyPaidVisitShouldBecomePaidWhenInvoicesPaid() {
        // visit A
        Visit visitA = new Visit();
        visitA = visitRepo.save(visitA);
        Invoice invoiceA1 = new Invoice(100, visitA.getId());
        Invoice invoiceA2 = new Invoice(150, visitA.getId());
        invoiceRepo.save(invoiceA1);
        invoiceRepo.save(invoiceA2);

        // visit B
        Visit visitB = new Visit();
        visitB = visitRepo.save(visitB);
        Invoice invoiceB1 = new Invoice(100, visitB.getId());
        Invoice invoiceB2 = new Invoice(150, visitB.getId());
        invoiceRepo.save(invoiceB1);
        invoiceRepo.save(invoiceB2);

        // invoice
        Map<InvoiceId, Integer> multipleInvoicesPayment = new HashMap<>();
        multipleInvoicesPayment.put(invoiceA1.getId(), 100);
        multipleInvoicesPayment.put(invoiceA2.getId(), 150);
        multipleInvoicesPayment.put(invoiceB1.getId(), 100);
        multipleInvoicesPayment.put(invoiceB2.getId(), 100); // 50 debt left
        paymentRegisterService.register(multipleInvoicesPayment);

        assertThat(visitA.isPaid()).isTrue();
        assertThat(invoiceA1.isPaid()).isTrue();
        assertThat(invoiceA2.isPaid()).isTrue();
        assertThat(visitB.isUnpaid()).isTrue();
        assertThat(invoiceB1.isPaid()).isTrue();
        assertThat(invoiceB2.isPaid()).isFalse();
    }


}
