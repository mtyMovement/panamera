package com.porsche.panamera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = { "com.porsche.panamera.core.common", "com.porsche.panamera.core.service","com.porsche.panamera.core.dal", "com.porsche.panamera.web"})
@MapperScan(basePackages = {"com.porsche.panamera.core.dal.dao"})
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class},scanBasePackages = "com.porsche.panamera")
public class PanameraApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanameraApplication.class, args);
    }

}
