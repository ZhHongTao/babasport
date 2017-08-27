package com.itheima.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.core.dao.product.product.ColorDao;
import com.itheima.core.dao.product.product.ProductDao;
import com.itheima.core.dao.product.product.SkuDao;
import com.itheima.product.pojo.product.Color;
import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.Sku;
import com.itheima.product.pojo.product.SkuQuery;

@Service(value="cmsService")
public class CmsServiceImpl implements CmsService{

	@Autowired
	private ProductDao productDao;
	@Override
	public Product selectProductById(Long id) {
		return productDao.selectByPrimaryKey(id);
	}
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Override
	public List<Sku> selectSkusByProductId(Long id) {
		SkuQuery example = new SkuQuery();
		example.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
		List<Sku> list = skuDao.selectByExample(example);
		for (Sku sku : list) {
			Color color = colorDao.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
		return list;
	}
	
}
