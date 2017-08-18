package com.itheima.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.core.pojo.TestTb;
import com.itheima.core.service.TestTbService;

@Controller
public class TestTbController {

	@Autowired
	private TestTbService testTbService;
	@RequestMapping(value="test/index.do")
	public String addTestTb(){
		TestTb tb = new TestTb();
		tb.setBirthday(new Date());
		tb.setName("张三");
		testTbService.addTestTb(tb);
		return "index";
	}
}
