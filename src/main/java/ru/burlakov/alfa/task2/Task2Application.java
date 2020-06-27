package ru.burlakov.alfa.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.burlakov.alfa.task2.repository")
public class Task2Application {

    private final Logger logger = LoggerFactory.getLogger(Task2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Task2Application.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties)  {

        return (evt) -> {
            try {
                Integer port = serverProperties.getPort();
                InetAddress ipAddr = InetAddress.getLocalHost();

                System.out.println("=========================");
                System.out.println(String.format("Server started: http://localhost:%s", port));
                System.out.println(String.format("Server started: http://%s:%s", ipAddr.getHostAddress(), port));
                System.out.println("=========================");
            } catch (UnknownHostException e) {
                logger.error(e.getMessage());
            }
        };
    }

}
