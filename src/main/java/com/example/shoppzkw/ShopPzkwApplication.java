package com.example.shoppzkw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ShopPzkwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPzkwApplication.class, args);
    }

}
