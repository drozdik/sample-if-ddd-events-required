package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Import(SampleAppConfig.class)
public class SampleDddEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleDddEventsApplication.class, args);
    }

}
