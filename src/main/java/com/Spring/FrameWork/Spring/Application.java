package com.Spring.FrameWork.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] beans = appContext.getBeanDefinitionNames();
        for (String bean : beans) {
            //System.out.println("Bean name: " + bean);
            Object object = appContext.getBean(bean);
            //System.out.println("Bean object:" + object);
        }
    }
}

/*
for remote debug of the spring boot Application

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
-jar .\spring-boot-hibernate-example-1.0-SNAPSHOT.jar


 */
