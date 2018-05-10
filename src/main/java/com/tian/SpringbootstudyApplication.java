package com.tian;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * 应用程序引导类, 也是spring的主要配置类.
 *
 * @SpringBootApplication 注解的作用是开启组件扫描和自动配置.
 *       我们可以简单的理解为该注解把其它三个注解的功能组合在了一起.
 *       spring的 @Configuration: 标明该类使用基于java的配置.
 *       spring的 @ComponentScan: 启用组件扫描,
 *       spring boot的 @EnableAutoConfiguration: 这个注解就是开启了spring boot的自动配置功能
 *
 *       @MapperScan 用来指定mybatis的扫描mapper接口的路径.  也可以直接在接口上加@Mapper注解
 *       @EnableJms 会启动jms的注解扫描
 */

@SpringBootApplication
//@MapperScan("com.tian.dao.mapper")
@EnableJms
public class SpringbootstudyApplication {

	@Bean(name = "testQueue1")
	public Queue queue(){
		return new ActiveMQQueue("sample.queue");
	}

	@Bean(name = "queue2")
	public Queue queue2(){
		return new ActiveMQQueue("queue2");
	}

	/**
	 * 启动项目的方法, 会启动内置的tomcat
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootstudyApplication.class, args);
	}
}
