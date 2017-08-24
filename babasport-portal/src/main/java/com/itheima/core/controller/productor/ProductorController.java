package com.itheima.core.controller.productor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.brand.service.BrandService;
import com.itheima.core.service.product.SearchService;
import com.itheima.page.Pagination;
import com.itheima.product.pojo.Brand;

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
	@Autowired
	private BrandService brandService;
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/search")
	public String search(String Qkeyword,Integer pageNo,Long Qbrand,String Qprice,String Qsort,Model model){
		Pagination pagination = searchService.searchByKeyword(Qkeyword,pageNo,Qbrand,Qprice,Qsort);
		List<Brand> brands = brandService.findBrandByQuery(null, null);
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
			if(Qprice.contains("-")){
				
				QpriceMap.put("价格范围", Qprice);
			}else{
				QpriceMap.put("价格", Qprice+"以上");
			}
			map.put("Qprice",QpriceMap );
		}
		model.addAttribute("map",map);
		
		return "search";
	}
}
