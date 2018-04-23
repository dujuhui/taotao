package com.taotao.order;

import com.taotao.common.utils.RedisUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
@MapperScan(basePackages = "com.taotao.manage.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaotaoOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoOrderApplication.class, args);
	}

	@Bean
	public RedisUtil getRedis(){
		return new RedisUtil();
	}
}
