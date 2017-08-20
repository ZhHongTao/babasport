package com.itheima.brand.service;

import java.util.List;

import com.itheima.product.pojo.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {

	public List<Brand> findBrandByQuery(String name, Integer isDisplay);

	public Pagination findBrandByQuery(String name, Integer isDisplay, Integer pageNo);

	public Brand findBrandById(Long id);

}
