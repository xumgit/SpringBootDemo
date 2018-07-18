package com.sts.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.sts.demo.dao"})
public class DemoSpringBootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootMybatisApplication.class, args);
	}
}
