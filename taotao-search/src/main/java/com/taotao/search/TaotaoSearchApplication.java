package com.taotao.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@MapperScan(basePackages = "com.taotao.search.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoSearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaotaoSearchApplication.class, args);
	}
}
