package com.taotao.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan(basePackages = "com.taotao.sso.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoSsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoSsoApplication.class, args);
	}
}
