package com.taotao.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.taotao.manage.service")
@SpringBootApplication
public class TaotaoPortalWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoPortalWebApplication.class, args);
	}
}
