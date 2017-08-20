package com.itheima.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台页面的入口程序
 * @author Administrator
 *
 */
@Controller
public class CenterController {

	@RequestMapping("/console/index.do")
	public String index(){
		return "index";
	}
	@RequestMapping("/console/top.do")
	public String top(){
		return "top";
	}
	@RequestMapping("/console/main.do")
	public String main(){
		return "main";
	}
	@RequestMapping("/console/left.do")
	public String left(){
		return "left";
	}
	@RequestMapping("/console/right.do")
	public String right(){
		return "right";
	}
	@RequestMapping("/console/frame/product_main.do")
	public String product_main(){
		return "frame/product_main";
	}
	@RequestMapping("/console/frame/product_left.do")
	public String product_left(){
		return "frame/product_left";
	}
	
	
}
