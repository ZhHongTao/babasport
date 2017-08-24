package com.itheima.core.service.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.brand.BrandDao;
import com.itheima.core.dao.product.product.ColorDao;
import com.itheima.core.dao.product.product.ProductDao;
import com.itheima.core.dao.product.product.SkuDao;
import com.itheima.product.pojo.product.Color;
import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.ProductQuery;
import com.itheima.product.pojo.product.ProductQuery.Criteria;
import com.itheima.product.pojo.product.Sku;
import com.itheima.product.pojo.product.SkuQuery;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private Jedis jedis;
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
		productQuery.setOrderByClause("id desc");
		Pagination pagination = new Pagination(productQuery.getPageNo(), 
				productQuery.getPageSize(),productDao.countByExample(productQuery));
		productQuery.setPageNo(pagination.getPageNo());
		pagination.setList(productDao.selectByExample(productQuery));
		pagination.pageView("/console/product/list.do", builder.toString());
		return pagination;
	}
	/**
	 * 添加商品
	 */
	@Autowired
	private SkuDao skuDao;
	@Override
	public void insertProduct(Product product) {
		Long pid = jedis.incr("pid");
		product.setId(pid);
		product.setIsShow(false);
		product.setIsDel(true);
		product.setCreateTime(new Date());
		productDao.insertSelective(product);
		for (String cId : product.getColors().split(",")) {
			for(String sId : product.getSizes().split(",")){
				Sku sku = new Sku();
				sku.setColorId(Long.valueOf(cId));
				sku.setSize(sId);
				sku.setProductId(product.getId());
				sku.setPrice(0f);
				sku.setDeliveFee(4f);
				sku.setStock(200);
				sku.setUpperLimit(1222);
				sku.setCreateTime(new Date());
				skuDao.insertSelective(sku);
			}
		}
	}
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private ColorDao colorDao;
	@Override
	public Product findProductById(Long id) {
		Product product = productDao.selectByPrimaryKey(id);
		product.setBrandName(brandDao.findBrandById(product.getBrandId()).getName());
		String colors = product.getColors();
		List<Color> colorList = new ArrayList<>(colors.split(",").length);
		StringBuilder b = new StringBuilder();
		for(String cid : colors.split(",")){
			Color color = colorDao.selectNameById(Long.valueOf(cid));
			b.append(color.getName()).append(",");
			colorList.add(color);
		}
		product.setColors(b.toString());
		product.setColorList(colorList);
		return product;
	}
	/**
	 * 上架操作
	 */
	@Autowired
	private HttpSolrServer solrServer;
	@Override
	public void isShow(String ids) throws Exception{
		//更改数据库中商品的状态为上架
		//创建商品对象并id和上架状态
		Product product = new Product();
		product.setIsShow(true);
		for(String id:ids.split(",")){
			product.setId(Long.valueOf(id));
			productDao.updateByPrimaryKeySelective(product);
			//更改完为上架状态后要将他的信息放到索引库中
			//创建document对象
			Product p = productDao.selectByPrimaryKey(Long.valueOf(id));
		    SolrInputDocument doc = new SolrInputDocument();
		    doc.setField("id",p.getId());
		    doc.setField("name_ik", p.getName());
		    doc.setField("brandId", p.getBrandId());
		    doc.setField("url",p.getImgUrl());
		    SkuQuery skuQuery = new SkuQuery();
		    skuQuery.createCriteria().andProductIdEqualTo(Long.valueOf(id));
		    skuQuery.setOrderByClause("price asc");
		    skuQuery.setStartRow(1);
		    skuQuery.setPageSize(1);
		    List<Sku> skus = skuDao.selectByExample(skuQuery);
		    doc.setField("price", skus.get(0).getPrice());
			solrServer.add(doc, 1000);
			//静态化
		}
	}
	/**
	 * 下架操作
	 */
	
	@Override
	public void isNotShow(String ids) {
		Product product = new Product();
		product.setIsShow(false);
		for(String id:ids.split(",")){
			product.setId(Long.valueOf(id));
			productDao.updateByPrimaryKeySelective(product);
		}
	}
	

}

















