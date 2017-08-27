package com.itheima.brand.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.brand.BrandDao;
import com.itheima.core.web.Constants;
import com.itheima.product.pojo.brand.Brand;
import com.itheima.product.pojo.brand.BrandQuery;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandDao brandDao;
	
	/**
	 * 条件查询
	 */
	@Override
	public List<Brand> findBrandByQuery(String name, Integer isDisplay) {
		BrandQuery query = new BrandQuery();
		
		if(null != name){
			query.setName(name);
			
		}
		if(null != isDisplay){
			query.setIsDisplay(isDisplay);
			
		}else{
			query.setIsDisplay(1);
			
		}
		query.setStartRow(null);
		return brandDao.findBrandByQuery(query);
	}
	
	/**
	 * 分页查询返回分页对象
	 * @param name
	 * @param isDisplay
	 * @param pageNo
	 * @return
	 */
	@Override
	public Pagination findBrandByQuery(String name, Integer isDisplay, Integer pageNo) {
		BrandQuery query = new BrandQuery();
		StringBuilder params = new StringBuilder();
		
		if(null != name){
			query.setName(name);
			params.append("Qname=").append(name);
		}
		if(null != isDisplay){
			query.setIsDisplay(isDisplay);
			params.append("&QisDisplay").append(isDisplay);
		}else{
			query.setIsDisplay(1);
			params.append("&QisDisplay").append(1);
		}
		
		query.setPageSize(5);
		//分页结果集
		int totalCount = brandDao.getTotalCountByQuery(query);
		Pagination pagination = new Pagination(Pagination.cpn(pageNo),5,totalCount);
		//当前页
		query.setPageNo(pagination.getPageNo());
		//每页的数量
		pagination.setPageSize(query.getPageSize());
		//总条数
		
		pagination.setTotalCount(totalCount);
		//结果集
		pagination.setList(brandDao.findBrandByQuery(query));
		//设置pageView
		String url = "/console/brand/list.do";
		
		pagination.pageView(url, params.toString());
		
		return pagination;
	}
	
	/**
	 * 根据id查询品牌并返回
	 * @param id
	 * @param model
	 * @return
	 */
	@Override
	public Brand findBrandById(Long id) {
		
		return brandDao.findBrandById(id);
	}
	
	/**
	 * 根据id修改品牌
	 * @param brand
	 * @return
	 */
	@Autowired
	private Jedis jedis;
	@Override
	public void edit(Brand brand) {
		brandDao.edit(brand);
		jedis.hset(Constants.BRAND, String.valueOf(brand.getId()), brand.getName());
	}
	
	/**
	 * 添加品牌
	 * @param brand
	 * @return
	 */
	@Override
	public void add(Brand brand) {
		brandDao.add(brand);
		//如果更改状态为不可用  就会从redis缓存中删除
		if(brand.getIsDisplay()==1){
			jedis.hset(Constants.BRAND,String.valueOf(brand.getId()), brand.getName());
		}else{
			jedis.hdel(Constants.BRAND, String.valueOf(String.valueOf(brand.getId())));
		}
		
	}
	/**
	 * 根据id删除一个品牌
	 * @param id
	 * @return
	 */
	@Override
	public void deleteOneById(Integer id) {
		brandDao.deleteOneById(id);
		jedis.hdel(Constants.BRAND, String.valueOf(id));
	}

	/**
	 * 根据id批量删除
	 */
	@Override
	public void deleteByIds(String ids) {
		String[] arr = ids.split(",");
		for (String str : arr) {
			Integer id = Integer.valueOf(str);
			deleteOneById(id);
		}
	}

}
