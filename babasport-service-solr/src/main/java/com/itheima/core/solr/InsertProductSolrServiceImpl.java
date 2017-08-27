package com.itheima.core.solr;

import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.core.dao.product.product.ProductDao;
import com.itheima.core.dao.product.product.SkuDao;
import com.itheima.core.service.product.InsertProductSolrService;
import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.Sku;
import com.itheima.product.pojo.product.SkuQuery;

@Service
public class InsertProductSolrServiceImpl implements InsertProductSolrService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private HttpSolrServer solrServer;
	public void insertProductToSolr(Long id){
		//更改完为上架状态后要将他的信息放到索引库中
		//创建document对象
		Product p = productDao.selectByPrimaryKey(Long.valueOf(id));
	    SolrInputDocument doc = new SolrInputDocument();
	    doc.setField("id",p.getId());
	    doc.setField("name_ik", p.getName());
	    doc.setField("brandId", p.getBrandId());
	    doc.setField("url",p.getImgUrl());
	    SkuQuery skuQuery = new SkuQuery();
	    skuQuery.createCriteria().andProductIdEqualTo(Long.valueOf(id)).andStockGreaterThan(0);
	    skuQuery.setOrderByClause("price asc");
	    skuQuery.setStartRow(1);
	    skuQuery.setPageSize(1);
	    List<Sku> skus = skuDao.selectByExample(skuQuery);
	    doc.setField("price", skus.get(0).getPrice());
		try {
			solrServer.add(doc, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
