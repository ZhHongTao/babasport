package com.itheima.core.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.product.product.ProductDao;
import com.itheima.product.pojo.product.ProductQuery;
import com.itheima.product.pojo.product.ProductQuery.Criteria;

import cn.itcast.common.page.Pagination;
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	/**
	 * 条件分页查询分页对象
	 */
	@Override
	public Pagination findPaginationByQuery(Integer pageNo, String qname, Long qbrandId, Boolean qisShow) {
		ProductQuery productQuery = new ProductQuery();
		Criteria createCriteria = productQuery.createCriteria();
		StringBuilder builder = new StringBuilder();
		if(qname!=null){
			createCriteria.andNameLike("%"+qname+"%");
			builder.append("Qname=").append(qname);
		}
		if(null != qbrandId){
			createCriteria.andBrandIdEqualTo(qbrandId);
			builder.append("&QbrandId=").append(qbrandId);
		}
		if(null != qisShow){
			createCriteria.andIsShowEqualTo(qisShow);
			builder.append("&qisShow=").append(qisShow);
		}else{
			createCriteria.andIsShowEqualTo(false);
			builder.append("&qisShow=").append(false);
		}
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(10);
		Pagination pagination = new Pagination(productQuery.getPageNo(), 
				productQuery.getPageSize(),productDao.countByExample(productQuery));
		productQuery.setPageNo(pagination.getPageNo());
		pagination.setList(productDao.selectByExample(productQuery));
		pagination.pageView("/console/product/list.do", builder.toString());
		return pagination;
	}

}
