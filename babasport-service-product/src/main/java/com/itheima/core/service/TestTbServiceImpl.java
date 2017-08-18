package com.itheima.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.TestTbDao;
import com.itheima.core.pojo.TestTb;
import com.itheima.core.service.TestTbService;

@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {

	@Autowired
	private TestTbDao testTbDao;
	@Override
	public void addTestTb(TestTb testTb) {
		testTbDao.addTestTb(testTb);
		
	}

}
