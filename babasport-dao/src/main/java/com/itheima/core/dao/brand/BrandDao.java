package com.itheima.core.dao.brand;

import java.util.List;

import com.itheima.product.pojo.Brand;
import com.itheima.product.pojo.BrandQuery;

public interface BrandDao {

	/**
	 * 根据query条件查询品牌
	 * @param query
	 * @return
	 */
	public List<Brand> findBrandByQuery(BrandQuery query);

	/**
	 * 根据query条件查询品牌数量
	 * @param query
	 * @return
	 */
	public int getTotalCountByQuery(BrandQuery query);

	/**
	 * 根据id获取品牌
	 * @param id
	 * @return
	 */
	public Brand findBrandById(Long id);

	/**
	 * 根据id修改品牌
	 * @param brand
	 * @return
	 */
	public void edit(Brand brand);

	/**
	 * 添加品牌
	 * @param brand
	 * @return
	 */
	public void add(Brand brand);

	/**
	 * 根据id删除一个品牌
	 * @param id
	 * @return
	 */
	public void deleteOneById(Integer id);

}
