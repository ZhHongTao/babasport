package com.itheima.core.service;

import java.util.List;

import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.Sku;

public interface CmsService {

	public Product selectProductById(Long id);

	public List<Sku> selectSkusByProductId(Long id);

}
