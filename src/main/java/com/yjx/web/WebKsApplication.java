package com.yjx.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yjx.web.mapper")
public class WebKsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebKsApplication.class, args);
    }

}
