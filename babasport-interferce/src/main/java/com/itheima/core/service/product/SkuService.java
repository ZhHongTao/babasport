package com.itheima.core.service.product;

import java.util.List;

import com.itheima.product.pojo.product.Sku;

public interface SkuService {

	/**
	 * 通过商品id查询商品库存
	 * @param productId
	 * @return
	 */
	public List<Sku> findSkyByProductId(Long productId);

	/**
	 * 修改库存
	 * @param sku
	 */
	public void updateById(Sku sku);

}
