package com.tian;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tian.common.handler.PageInterceptor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Session;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
 *       @EnableCaching 启用缓存注解
 *       @EnableAspectJAutoProxy 开启切面功能  默认就是开启的
 */

@SpringBootApplication
//@MapperScan("com.tian.dao.mapper")
@EnableJms
@EnableCaching
//@EnableAspectJAutoProxy
public class SpringbootstudyApplication extends SpringBootServletInitializer {
	/**
	 * mybatis分页插件(只支持mysql的)
	 * @return
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setPlugins(new Interceptor[]{new PageInterceptor()});
		return sqlSessionFactoryBean;
	}

	/**
	 * 显式定义这个bean,修改属性,使得可以格式化返回的时间格式,空属性等
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		MappingJackson2HttpMessageConverter m = new MappingJackson2HttpMessageConverter();
		ObjectMapper o = new ObjectMapper();
		o.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		o.setSerializationInclusion(NON_NULL);
		m.setObjectMapper(o);
		return m;
	}

	/**
	 * 配置消息的确认, 重发机制
	 * @return
	 */
	@Bean
	public RedeliveryPolicy redeliveryPolicy(){
		RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
		//是否在每次尝试重新发送失败后,增长这个等待时间
		redeliveryPolicy.setUseExponentialBackOff(true);
		//重发次数,默认为6次   这里设置为10次
		redeliveryPolicy.setMaximumRedeliveries(10);
		//重发时间间隔,默认为1秒
		redeliveryPolicy.setInitialRedeliveryDelay(1);
		//第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
		redeliveryPolicy.setBackOffMultiplier(2);
		//是否避免消息碰撞
		redeliveryPolicy.setUseCollisionAvoidance(false);
		//设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
		redeliveryPolicy.setMaximumRedeliveryDelay(-1);
		return redeliveryPolicy;
	}

	/**
	 * 声明一个activemq连接工厂
	 * @param url
	 * @param username
	 * @param password
	 * @param redeliveryPolicy
	 * @return
	 */
	@Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${spring.activemq.broker-url}")String url,
															   @Value("${spring.activemq.user}") String username, @Value("${spring.activemq.password}") String password, RedeliveryPolicy redeliveryPolicy){
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(username,password,url);
		activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
		return activeMQConnectionFactory;
	}

	/**
	 *
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory ){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		jmsTemplate.setSessionTransacted(true);
		return jmsTemplate;
	}

	@Bean(name = "testQueue1")
	public Queue queue(){
		return new ActiveMQQueue("sample.queue");
	}

	/**
	 * 启动项目的方法, 会启动内置的tomcat
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootstudyApplication.class, args);
	}

	/**
	 * 本类,如上继承一个类, 重写下面这个方法, 然后修改maven中的package为war包.
	 * 然后运行maven的命令package就可以打一个war包了.直接放到tomcat中就可以运行
	 * @param builder
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootstudyApplication.class);
	}
}
