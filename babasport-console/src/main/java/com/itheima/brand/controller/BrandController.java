package com.itheima.brand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.brand.service.BrandService;
import com.itheima.product.pojo.Brand;

import cn.itcast.common.page.Pagination;

/**
 * 品牌管理
 * @author Administrator
 *
 */
@Controller
public class BrandController {

	@Autowired 
	private BrandService brandService;
	@RequestMapping(value="/console/brand/list.do")
	public String list(String name,Integer isDisplay,Model model,Integer pageNo){
		Pagination pageBean = brandService.findBrandByQuery(name,isDisplay,pageNo);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		return "brand/list";
	}
	@RequestMapping(value="/console/brand/toEdit.do")
	public String toEdit(Long id,Model model){
		Brand brand = brandService.findBrandById(id);
		model.addAttribute("brand",brand);
		return "brand/edit";
	}
}
