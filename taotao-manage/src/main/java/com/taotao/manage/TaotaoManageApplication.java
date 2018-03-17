package com.taotao.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Repository;

@MapperScan(basePackages = "com.taotao.manage.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoManageApplication.class, args);
	}
}
