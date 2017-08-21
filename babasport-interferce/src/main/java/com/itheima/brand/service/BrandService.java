package com.itheima.brand.service;

import java.util.List;

import com.itheima.product.pojo.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {

	/**
	 * 条件查询
	 */
	public List<Brand> findBrandByQuery(String name, Integer isDisplay);

	/**
	 * 分页查询返回分页对象
	 * @param name
	 * @param isDisplay
	 * @param pageNo
	 * @return
	 */
	public Pagination findBrandByQuery(String name, Integer isDisplay, Integer pageNo);

	/**
	 * 根据id查询品牌并返回
	 * @param id
	 * @param model
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

	/**
	 * 根据id批量删除
	 * @param ids
	 */
	public void deleteByIds(String ids);

}
