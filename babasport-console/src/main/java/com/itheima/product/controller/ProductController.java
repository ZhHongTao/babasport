package com.itheima.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品的控制层
 * @author Administrator
 *
 */
@Controller
public class ProductController {

	@RequestMapping(value="/console/product/list.do")
	public String list(){
		
		return "product/list";
	}
}
