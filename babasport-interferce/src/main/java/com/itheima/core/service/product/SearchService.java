package com.itheima.core.service.product;

import com.itheima.page.Pagination;

public interface SearchService {

	//根据关键字查询
	Pagination searchByKeyword(String qkeyword, Integer pageNo, Long qbrand, String qprice, String Qsort);

}
