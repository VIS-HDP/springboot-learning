package com.vis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//@MapperScan({"com.vis.demo.jta.mapper","com.vis.demo.jta.busmapper"})
@EnableTransactionManagement
public class SpringbootJtaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJtaApplication.class, args);
	}

}
