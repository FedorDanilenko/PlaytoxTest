package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
@EnableScheduling
public class Main {
    public void start(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public static void main(String[] args) {
        new Main().start(args);
    }
}