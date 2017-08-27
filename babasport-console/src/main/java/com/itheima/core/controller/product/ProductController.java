package com.itheima.core.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.brand.service.BrandService;
import com.itheima.core.service.product.ColorService;
import com.itheima.core.service.product.ProductService;
import com.itheima.product.pojo.brand.Brand;
import com.itheima.product.pojo.product.Color;
import com.itheima.product.pojo.product.Product;

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
	@Autowired
	private ColorService colorService;
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
	@RequestMapping(value="/console/product/toAdd.do")
	public String toadd(Model model){
		//品牌结果集
		List<Brand> brands = brandService.findBrandByQuery(null, null);
		model.addAttribute("brands", brands);
		List<Color> colors = colorService.findAllColor();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	@RequestMapping(value="/console/product/add.do")
	public String add(Product product){
		productService.insertProduct(product);
		return "redirect:/console/product/list.do";
	}
	/**
	 * 查看商品
	 * @param id
	 * @param Qname
	 * @param QisShow
	 * @param pageNo
	 * @param QbrandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/console/product/look.do")
	public String look(Long id,String Qname,boolean QisShow,Integer pageNo,Long QbrandId,Model model){
		Product product = productService.findProductById(id);
		model.addAttribute("product", product);
		//品牌结果集
		List<Brand> brands = brandService.findBrandByQuery(null, null);
		model.addAttribute("brands", brands);
		//颜色结果集
		List<Color> colors = colorService.findAllColor();
		model.addAttribute("colors", colors);
		model.addAttribute("QisShow", QisShow);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("QbrandId", QbrandId);
		model.addAttribute("Qname", Qname);
		return "product/look";
	}
	/**
	 * 去修改页面
	 * @param id
	 * @param Qname
	 * @param QisShow
	 * @param pageNo
	 * @param QbrandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/console/product/toEdit.do")
	public String toEdit(Long id,String Qname,boolean QisShow,Integer pageNo,Long QbrandId,Model model){
		Product product = productService.findProductById(id);
		
		List<Brand> brands = brandService.findBrandByQuery(null, null);
		model.addAttribute("brands", brands);
		List<Color> colors = colorService.findAllColor();
		model.addAttribute("colors", colors);
		
		model.addAttribute("product", product);
		model.addAttribute("QisShow", QisShow);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("QbrandId", QbrandId);
		model.addAttribute("Qname", Qname);
		return "product/edit";
	}
	/**
	 * 商品修改
	 * @param product
	 * @param Qname
	 * @param QisShow
	 * @param pageNo
	 * @param QbrandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/console/product/edit.do")
	public String edit(Product product,String Qname,boolean QisShow,Integer pageNo,Long QbrandId,Model model){
		productService.edit(product);
		
		
		return "forward:/console/product/list.do";
	}
	//上架
	@RequestMapping(value="/console/product/isShow.do")
	public String isShow(String id,String Qname,boolean QisShow,Integer pageNo,Long QbrandId,Model model) throws Exception{
		productService.isShow(id);
		return "forward:/console/product/list.do";
	}
	//下架
	@RequestMapping(value="/console/product/isNotShow.do")
	public String isNotShow(String id,String Qname,boolean QisShow,Integer pageNo,Long QbrandId,Model model){
		productService.isNotShow(id);
		
		return "forward:/console/product/list.do";
	}
	
}
