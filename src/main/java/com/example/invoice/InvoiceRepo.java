package com.example.invoice;

import com.example.SimpleRepo;

public class InvoiceRepo extends SimpleRepo<InvoiceId, Invoice> {
    public InvoiceRepo() {
        super(InvoiceId.class);
    }
}
