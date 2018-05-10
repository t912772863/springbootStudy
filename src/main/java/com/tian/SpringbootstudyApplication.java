package com.tian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 应用程序引导类, 也是spring的主要配置类.
 *
 * @SpringBootApplication 注解的作用是开启组件扫描和自动配置.
 *       我们可以简单的理解为该注解把其它三个注解的功能组合在了一起.
 *       spring的 @Configuration: 标明该类使用基于java的配置.
 *       spring的 @ComponentScan: 启用组件扫描,
 *       spring boot的 @EnableAutoConfiguration: 这个注解就是开启了spring boot的自动配置功能
 */
@SpringBootApplication
@MapperScan("com.tian.dao.mapper")
public class SpringbootstudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootstudyApplication.class, args);
	}
}
