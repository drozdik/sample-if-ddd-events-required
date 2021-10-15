package com.example.invoice;

import com.example.visit.VisitId;
import com.example.visit.VisitRepo;
import org.springframework.context.ApplicationEvent;

public class AllVisitInvoicesPaidEvent extends ApplicationEvent {

    private final VisitId visitId;

    public AllVisitInvoicesPaidEvent(Object source, VisitId visitId) {
        super(source);
        this.visitId = visitId;
    }

    public VisitId visitId() {
        return visitId;
    }
}
