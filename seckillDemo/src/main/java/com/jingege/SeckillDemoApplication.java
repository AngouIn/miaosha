package com.jingege;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jingege.mapper")
public class SeckillDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillDemoApplication.class,args);
    }
}
