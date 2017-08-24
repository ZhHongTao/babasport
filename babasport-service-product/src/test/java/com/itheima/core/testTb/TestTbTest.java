package com.itheima.core.testTb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class TestTbTest {

	
	@Test
	public void testJedis(){
	    Jedis j =new Jedis("192.168.200.128", 6379);
	    String string = j.get("pid");
	    System.out.println(string);
	    Long incr = j.incr("pid");
	    System.out.println(incr);
	}
	
}
