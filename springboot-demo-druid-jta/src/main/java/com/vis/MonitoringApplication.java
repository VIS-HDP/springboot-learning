package com.vis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * nohup java -jar /opt/local/monitoring.jar --spring.profiles.active=prod &
 */
@SpringBootApplication
//@MapperScan({"com.vis.demo.jta.mapper","com.vis.demo.jta.busmapper"})
@EnableTransactionManagement
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
}
