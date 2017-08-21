package com.itheima.core.service.product;

import cn.itcast.common.page.Pagination;
/**
 * 商品管理的接口
 * @author Administrator
 *
 */
public interface ProductService {

	/**
	 * 条件分页查询商品分页对象
	 * @param pageNo
	 * @param qname
	 * @param qbrandId
	 * @param qiShow
	 * @return
	 */
	public Pagination findPaginationByQuery(Integer pageNo, String qname, Long qbrandId, Boolean qiShow);

}
