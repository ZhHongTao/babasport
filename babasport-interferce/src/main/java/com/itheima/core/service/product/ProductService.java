package com.itheima.core.service.product;

import com.itheima.product.pojo.product.Product;

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

	/**
	 * 添加商品
	 * @param product
	 */
	public void insertProduct(Product product);

	/**
	 * 通过id查询商品
	 * @param id
	 * @return
	 */
	public Product findProductById(Long id);

	/**
	 * 上架操作
	 * @param ids
	 * @throws Exception 
	 */
	public void isShow(String ids) throws Exception;

	//下架
	public void isNotShow(String id);

}
