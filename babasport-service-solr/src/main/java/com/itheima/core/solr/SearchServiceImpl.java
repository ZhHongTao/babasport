package com.itheima.core.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.core.service.product.SearchService;
import com.itheima.page.Pagination;
import com.itheima.product.pojo.product.Product;
import com.itheima.product.pojo.product.ProductQuery;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

	@Autowired
	private HttpSolrServer solrServer;
	@Override
	public Pagination searchByKeyword(String qkeyword,Integer pageNo,Long qbrand, String qprice,String Qsort){
		ProductQuery productQuery = new ProductQuery();
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		SolrQuery query = new SolrQuery();
		//设置page回显条件
		StringBuilder builder = new StringBuilder();
		//设置关键查询条件
		if(null!=qkeyword){
			query.setQuery(qkeyword);
			builder.append("Qkeyword=").append(qkeyword);
		}else{
			query.setQuery("*");
		}
		//设置过滤条件 按照品牌搜索
		if(null!=qbrand){
			query.addFilterQuery("brandId:"+qbrand);
			builder.append("&Qbrand=").append(qbrand);
		}
		//按照价格区间搜索
		if(null!=qprice){
			String[] split = qprice.split("-");
				query.addFilterQuery("price:["+split[0]+" TO "+split[1]+"]");
				builder.append("&Qprice=").append(qprice);
		}
		//设置默认查询filed域
		query.set("df", "name_ik");
		//设置按照价格升序
		if(Qsort!=null){
			if("0".equals(Qsort)){
				query.setSort("price", ORDER.desc);
				builder.append("&Qsort=").append(Qsort);
			}else{
				query.setSort("price", ORDER.asc);
				builder.append("&Qsort=").append(Qsort);
			}
		}
		
		//开始行
		query.setStart(productQuery.getStartRow());
		//总条数
		query.setRows(productQuery.getPageSize());
		//设置需要查询的字段
		query.set("fl", "id,name_ik,price,url");
		//开启高亮
		query.setHighlight(true);
		//设置高亮字段
		query.addHighlightField("name_ik");
		//设置高亮部分的头
		query.setHighlightSimplePre("<span style='color:red'>");
		//设置高亮的尾部
		query.setHighlightSimplePost("</span>");
		long totalCount=0L;
		List<Product> plist = new ArrayList<>();
		System.out.println(query);
		try {
			QueryResponse queryResponse = solrServer.query(query);
			SolrDocumentList docs = queryResponse.getResults();
			totalCount = docs.getNumFound();
			for (SolrDocument doc : docs) {
				Product p = new Product();
				//获取id
				String id = (String)doc.get("id");
				p.setId(Long.valueOf(id));
				//获取图片
				p.setImgUrl((String)doc.get("url"));
				//获取价格
				p.setPrice((Float)doc.get("price"));
				//获取高亮的名字
				//Map<id，Map<name:name1,name2...>>
				Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
				//通过键获取到这个id对应的map
				Map<String, List<String>> nameMap = map.get(id);
				//判断一下通过id获取到的map是不是为空
				if(nameMap.size()>0){
					List<String> list = nameMap.get("name_ik");
					p.setName(list.get(0));
				}else{
					p.setName((String)doc.get("name_ik"));
				}
				plist.add(p);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		Pagination pagination = new Pagination(productQuery.getPageNo(),
				productQuery.getPageSize() , (int)totalCount);
		pagination.setList(plist);
		//这只分页的的路径（页码对应的url）
		pagination.pageView("/search", builder.toString());
		return pagination;
	}

}
