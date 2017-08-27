package com.itheima.core.controller.productor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.core.service.CmsService;
import com.itheima.core.service.product.SearchService;
import com.itheima.page.Pagination;
import com.itheima.product.pojo.brand.Brand;
import com.itheima.product.pojo.product.Color;
import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.Sku;

/**
 * 页面控制
 * 商品控制
 * @author Administrator
 *
 */
@Controller
public class ProductorController {

	@Autowired
	private SearchService searchService;
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/search")
	public String search(String Qkeyword,Integer pageNo,Long Qbrand,String Qprice,String Qsort,Model model){
		Pagination pagination = searchService.searchByKeyword(Qkeyword,pageNo,Qbrand,Qprice,Qsort);
		List<Brand> brands = searchService.selectBrandList();
		model.addAttribute("brands", brands);
		model.addAttribute("pagination", pagination);
		model.addAttribute("Qkeyword",Qkeyword);
		model.addAttribute("Qbrand",Qbrand);
		model.addAttribute("Qprice",Qprice);
		model.addAttribute("Qsort",Qsort);
		//Map<String,String> map = new HashMap<>();
		Map<String,Map<String,String>> map = new HashMap<>(0);
		//品牌条件
		if(null!=Qbrand){
			for (Brand brand : brands) {
				if(Qbrand==brand.getId()){
					Map<String,String> brandMap = new HashMap<>(1);
					brandMap.put("品牌",brand.getName());
					map.put("Qbrand", brandMap);
				}
			}
		}
		//价格条件
		if(null!=Qprice){
			Map<String,String> QpriceMap = new HashMap<>(1);
			if(Qprice.contains("*")){
				QpriceMap.put("价格", Qprice.split("-")[0]+"以上");
			}else{
				QpriceMap.put("价格", Qprice);
				
			}
			map.put("Qprice",QpriceMap );
		}
		model.addAttribute("map",map);
		
		return "search";
	}
	@Autowired
	private CmsService cmsService;
	//商品详情
	@RequestMapping(value="/product/detail")
	public String detail(Long id,Model model){
		Product product = cmsService.selectProductById(id);
		model.addAttribute("product",product);
		
		List<Sku> skus = cmsService.selectSkusByProductId(id);
		model.addAttribute("skus",skus);
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		return "product";
	}
}
