package com.software;
import com.software.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@EnableCaching//开启缓存注解功能
@Slf4j
public class SoftWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoftWareApplication.class, args);
        log.info("server started");
    }
}
