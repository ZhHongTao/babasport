package com.itheima.core.solr.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.core.service.product.InsertProductSolrService;

public class ProductSolrMessageListener implements MessageListener{

	@Autowired
	private InsertProductSolrService insertProductSolrService;
	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage atm = (ActiveMQTextMessage)message;
		try {
			String pid =atm.getText();
			insertProductSolrService.insertProductToSolr(Long.valueOf(pid));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
