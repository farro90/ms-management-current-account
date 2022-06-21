package com.nttdata.bc19.msmanagementcurrentaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsManagementCurrentAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsManagementCurrentAccountApplication.class, args);
	}

}
