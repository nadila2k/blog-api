package com.nadila.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication

public class BlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

}
