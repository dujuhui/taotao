package com.taotao.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.taotao.manage.service")
@SpringBootApplication
public class TaotaoOrderWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoOrderWebApplication.class, args);
	}
}
