package com.example.payment;

import com.example.invoice.AllVisitInvoicesPaidEvent;
import com.example.invoice.Invoice;
import com.example.invoice.InvoiceId;
import com.example.invoice.InvoiceRepo;
import com.example.visit.VisitId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;

@Service
public class PaymentRegisterService {

    private final InvoiceRepo invoiceRepo;
    private final ApplicationEventPublisher eventPublisher;


    public PaymentRegisterService(InvoiceRepo invoiceRepo, ApplicationEventPublisher applicationEventPublisher) {
        this.invoiceRepo = invoiceRepo;
        this.eventPublisher = applicationEventPublisher;
    }

    /* with sync event we get rid of dependency on VisitRepo, but visitRepo used in same "Transaction", so Aggregate rule is violated. */
    public void register(Map<InvoiceId, Integer> multipleInvoicesPayment) {
        multipleInvoicesPayment.forEach((invoiceId, amount) -> {
            Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow();
            invoice.pay(amount);
            invoiceRepo.save(invoice);
            // find all invoices
            VisitId visitId = invoice.visitId();
            Stream<Invoice> sameVisitInvoices = invoiceRepo.findAll().stream().filter(inv -> inv.visitId().equals(visitId));
            // this could be an event, Invoices KNOW that all invoices of same visit were paid
            // fire event AllVisitInvoicesPaid
            if (sameVisitInvoices.noneMatch(Invoice::hasDebt)) {
                eventPublisher.publishEvent(new AllVisitInvoicesPaidEvent(this, visitId));
            }
        });
    }
}
