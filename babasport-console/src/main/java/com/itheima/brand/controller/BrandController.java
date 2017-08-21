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
	/**
	 * 条件查询加分页
	 */
	@RequestMapping(value="/console/brand/list.do")
	public String list(String Qname,Integer QisDisplay,Model model,Integer pageNo){
		Pagination pageBean = brandService.findBrandByQuery(Qname,QisDisplay,pageNo);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("Qname", Qname);
		model.addAttribute("QisDisplay", QisDisplay);
		return "brand/list";
	}
	/**
	 * 根据id查询品牌并返回到修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/console/brand/toEdit.do")
	public String toEdit(Long id,Model model,String Qname,Integer pageNo,Integer QisDisplay){
		Brand brand = brandService.findBrandById(id);
		model.addAttribute("brand",brand);
		model.addAttribute("Qname",Qname);
		model.addAttribute("pageNo",pageNo);
		model.addAttribute("QisDisplay",QisDisplay);
		return "brand/edit";
	}
	/**
	 * 根据id修改品牌
	 * @param brand
	 * @return
	 */
	@RequestMapping(value="/console/brand/edit.do")
	public String eidt(Brand brand,String Qname,Integer pageNo,Integer QisDisplay){
		brandService.edit(brand);
		return "forward:/console/brand/list.do";
	}
	/**
	 * 去添加页面
	 */
	@RequestMapping(value="/console/brand/toadd.do")
	public String toadd(){
		return "brand/add";
	}
	
	/**
	 * 添加品牌
	 * @param brand
	 * @return
	 */
	@RequestMapping(value="/console/brand/add.do")
	public String add(Brand brand){
		brandService.add(brand);
		return "redirect:/console/brand/list.do";
	}
	/**
	 * 根据id删除一个品牌
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/console/brand/deleteOne.do")
	public String deleteOneById(Integer id,String Qname,Integer pageNo,Integer QisDisplay){
		brandService.deleteOneById(id);
		return "forward:/console/brand/list.do";
	}
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/console/brand/deleteByIds.do")
	public String deleteByIds(String id,String Qname,Integer pageNo,Integer QisDisplay){
		brandService.deleteByIds(id);
		return "forward:/console/brand/list.do";
	}
	
	
}
