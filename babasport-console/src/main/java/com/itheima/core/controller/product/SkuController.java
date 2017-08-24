package com.itheima.core.controller.product;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.core.service.product.SkuService;
import com.itheima.product.pojo.product.Sku;

/**
 * 库存管理
 * @author Administrator
 *
 */
@Controller
public class SkuController {

	@Autowired
	private SkuService skuservice;
	@RequestMapping(value="/sku/list.do")
	public String findSkuByProductId(Long productId,Model model){
		List<Sku> skus = skuservice.findSkyByProductId(productId);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
	@RequestMapping(value="/sku/addSku.do")
	public void update(Sku sku,HttpServletResponse response) throws Exception{
		JSONObject jo = new JSONObject();
		try {
			skuservice.updateById(sku);
			jo.put("message", 1);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("message",0);
		}
		response.setContentType("application/json;utf-8");
		response.getWriter().write(jo.toString());
	}
}
