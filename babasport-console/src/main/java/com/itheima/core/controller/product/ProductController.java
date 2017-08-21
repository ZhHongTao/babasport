package com.itheima.core.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.brand.service.BrandService;
import com.itheima.core.service.product.ProductService;
import com.itheima.product.pojo.Brand;

import cn.itcast.common.page.Pagination;

/**
 * 商品的控制层
 * @author Administrator
 *
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@RequestMapping(value="/console/product/list.do")
	public String list(Integer pageNo,String Qname,Long QbrandId,Boolean QisShow,Model model){
		Pagination pageBean = productService.findPaginationByQuery(pageNo,Qname,QbrandId,QisShow);
		model.addAttribute("pageBean", pageBean);
		List<Brand> brands = brandService.findBrandByQuery(null, null);
		model.addAttribute("brands", brands);
		model.addAttribute("Qname", Qname);
		model.addAttribute("QbrandId", QbrandId);
		model.addAttribute("QisShow", QisShow);
		return "product/list";
	}
	/**
	 * 去添加页面
	 */
	@RequestMapping(value="/console/product/add.do")
	public String toadd(Model model){
		
		return "product/add";
	}
}
