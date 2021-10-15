package com.example.payment;

import com.example.invoice.Invoice;
import com.example.invoice.InvoiceId;
import com.example.invoice.InvoiceRepo;
import com.example.visit.Visit;
import com.example.visit.VisitId;
import com.example.visit.VisitRepo;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaymentRegisterService {

    private final InvoiceRepo invoiceRepo;
    private final VisitRepo visitRepo;

    public PaymentRegisterService(InvoiceRepo invoiceRepo, VisitRepo visitRepo) {
        this.invoiceRepo = invoiceRepo;
        this.visitRepo = visitRepo;
    }

    public void register(int amount, InvoiceId invoiceId) {
        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow();
        invoice.pay(amount);
        invoiceRepo.save(invoice);
        /* one way with domain service */
        if (invoice.isPaid()) {
            Visit visit = visitRepo.findById(invoice.visitId()).orElseThrow();
            visit.markPaid();
            visitRepo.save(visit);
        }
    }

    // let's review such solution.. dependencies, what can break
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
                Visit visit = visitRepo.findById(invoice.visitId()).orElseThrow();
                visit.markPaid();
                visitRepo.save(visit);
            }
        });
    }
}
