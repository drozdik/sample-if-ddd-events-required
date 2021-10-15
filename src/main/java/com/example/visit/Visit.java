package com.example.visit;

import com.example.Entity;

public class Visit extends Entity<VisitId> {

    private Status status = Status.UNPAID;

    public void markPaid() {
        status = Status.PAID;
    }

    public boolean isPaid() {
        return status == Status.PAID;
    }

    public boolean isUnpaid() {
        return !isPaid();
    }

    enum Status {
        UNPAID, PAID
    }
}
