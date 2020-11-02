package com.example.ordertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//允许kafka
@EnableKafka
//swagger开启
@EnableSwagger2
public class OrdertestApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdertestApplication.class, args);
    }
}