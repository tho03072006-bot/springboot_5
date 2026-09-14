package edu.hcmute.webpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Lớp khởi động (entry point) của ứng dụng Spring Boot.
 * Chạy class này (Run As > Spring Boot App trong Eclipse/STS, hoặc "mvn spring-boot:run")
 * để khởi động server nhúng (embedded Tomcat) của Spring Boot.
 */
@SpringBootApplication
public class Springboot5Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot5Application.class, args);
    }

}
