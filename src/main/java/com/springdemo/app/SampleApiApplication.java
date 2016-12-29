package com.springdemo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.springdemo.contracts")
public class SampleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApiApplication.class, args);
    }
}
