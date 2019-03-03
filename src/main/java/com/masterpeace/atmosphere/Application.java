package com.masterpeace.atmosphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.masterpeace")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
