package com.example.invoice;

import com.example.Entity;
import com.example.visit.VisitId;

public class Invoice extends Entity<InvoiceId> {

    int total;
    int paid;
    private final VisitId visitId;

    public Invoice(int total, VisitId visitId) {
        this.total = total;
        this.visitId = visitId;
    }

    public void pay(int amount) {
        paid += amount;
    }

    public boolean isPaid() {
        return total == paid;
    }

    public VisitId visitId() {
        return visitId;
    }

    public boolean hasDebt() {
        return !isPaid();
    }
}
