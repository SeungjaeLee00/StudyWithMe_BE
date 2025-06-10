package org.seungjae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StudyWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyWithMeApplication.class, args);
    }
}
