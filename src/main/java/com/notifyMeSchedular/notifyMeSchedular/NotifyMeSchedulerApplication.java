package com.notifyMeSchedular.notifyMeSchedular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotifyMeSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyMeSchedulerApplication.class, args);
	}

}
