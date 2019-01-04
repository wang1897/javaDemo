package com.demo;

import com.demo.service.ContractService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        // 测试功能
        ContractService.getInstance().testDemo();
    }
}
