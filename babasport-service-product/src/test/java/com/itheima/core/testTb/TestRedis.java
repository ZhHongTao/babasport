package com.itheima.core.testTb;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.core.pojo.TestTb;
import com.itheima.core.service.TestTbService;
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestRedis {

	/*@Autowired
	private TestTbDao testTbDao;*/
	@Autowired
	private TestTbService testTbService;
	@Test
	public void testTbAdd(){
		TestTb tb = new TestTb();
		tb.setBirthday(new Date());
		tb.setName("张三");
		testTbService.addTestTb(tb);
	}
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application-context.xml");
		Object bean = ac.getBean("testTbService");
		System.out.println(bean);
	}
}
