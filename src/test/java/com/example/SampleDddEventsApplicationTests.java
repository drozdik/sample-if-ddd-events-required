package com.example;

import com.example.invoice.InvoiceRepo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Tag("integration")
class SampleDddEventsApplicationTests {

    @Autowired
    InvoiceRepo invoiceRepo;

    @Test
    void contextLoads() {
        assertThat(invoiceRepo).isNotNull();
    }

}
