package com.itheima.core.testTb;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private HttpSolrServer solrServer;
	@Test
	public void testSolrDelete() throws Exception{
		solrServer.deleteById("510");
		solrServer.commit();
	}
	@Test
	public void testSolrDelete2() throws Exception{
		UpdateResponse query = solrServer.deleteByQuery("name:李四");
		String requestUrl = query.getRequestUrl();
		System.out.println(requestUrl);
		solrServer.commit();
	}
	@Test
	public void addSole() throws Exception{
		SolrQuery query = new SolrQuery();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "张三");
		doc.addField("name", "李四");
		solrServer.add(doc);
		solrServer.commit();
	}
	@Test
	public void addSole2() throws Exception{
		SolrQuery query = new SolrQuery();
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", "张四");
		doc.setField("name", "李四");
		solrServer.add(doc);
		solrServer.commit();
	}
}
