package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes= CommunityApplication.class) //使用和开发部分相同的配置类
class CommunityApplicationTests implements ApplicationContextAware {

	@Test
	void contextLoads() {
	}
	private ApplicationContext applicationContext; //容器

	//applicationContext就是Spring容器，BeanFactory的子接口
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println(this.applicationContext);
		// 获取容器自动装配的bean
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		// get by name cannot tell the type, needs casting or specify the type
		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());
		/*
		笔记：
		通过面向接口编程，需要引入新的Bean的时候直接增加就好了。
		 */
	}

	@Test
	public void testBeanManagement(){
		AlphaService alphaservice = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaservice);
		alphaservice = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaservice);
		/*
		笔记：
		程序启动的时候这个Bean被实例化，停止的时候Bean被销毁，就说明这个Bean是单例的。
		我们Web程序就是只启动一次。一般情况都是Singleton，但我们其实可以用prototype
		每次访问Bean的时候都会给一个新的实例，同时 也不会call到@preDestroy的方法
		 */
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat= applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
		/*
		笔记：
		这种方式是主动获取容器，但其实这样很麻烦。既然是依赖注入，就不要主动获取了。
		 */
	}

	// ===================Autowired is auto injection of dependencies============================//
	@Autowired
	private AlphaDao alphaDao;

	@Autowired
	@Qualifier("alphaHibernate")
	private AlphaDao alphaHibernate;

	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI(){
		System.out.println(alphaDao);
		System.out.println(alphaHibernate);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}

	/*
	调用链路：
	用Controller处理浏览器的请求 会调用业务组件Service来处理当前的业务，业务组件进一步调用DAO访问数据库。
	1.6课时到此为止，主要就是介绍了IoC的基础用法从主动调用到自动注入等等。
	 */

}
