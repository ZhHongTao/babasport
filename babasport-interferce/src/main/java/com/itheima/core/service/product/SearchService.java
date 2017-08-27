package com.itheima.core.service.product;

import java.util.List;

import com.itheima.page.Pagination;
import com.itheima.product.pojo.brand.Brand;

public interface SearchService {

	//根据关键字查询
	public Pagination searchByKeyword(String qkeyword, Integer pageNo, Long qbrand, String qprice, String Qsort);

	public List<Brand> selectBrandList();

}
