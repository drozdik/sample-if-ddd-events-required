package com.example;

import com.example.invoice.InvoiceRepo;
import com.example.visit.VisitRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleAppConfig {

    @Bean
    public InvoiceRepo invoiceRepo(){
        return new InvoiceRepo();
    }

    @Bean
    public VisitRepo visitRepo() {
        return new VisitRepo();
    }
}
