package com.example.visit;

import com.example.invoice.AllVisitInvoicesPaidEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VisitService implements ApplicationListener<AllVisitInvoicesPaidEvent> {

    private final VisitRepo visitRepo;

    public VisitService(VisitRepo visitRepo) {
        this.visitRepo = visitRepo;
    }

    @Override
    public void onApplicationEvent(AllVisitInvoicesPaidEvent event) {
        Visit visit = visitRepo.findById(event.visitId()).orElseThrow();
                visit.markPaid();
                visitRepo.save(visit);
    }
}
