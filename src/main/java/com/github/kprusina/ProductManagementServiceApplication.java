package com.github.kprusina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.kprusina.client.hnb_client")
public class ProductManagementServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductManagementServiceApplication.class, args);
  }
}
