package com.taotao.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan(basePackages = "com.taotao.content.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoContentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoContentApplication.class, args);
	}
}
