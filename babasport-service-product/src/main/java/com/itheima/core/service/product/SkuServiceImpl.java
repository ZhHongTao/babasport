package com.itheima.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.product.product.ColorDao;
import com.itheima.core.dao.product.product.SkuDao;
import com.itheima.product.pojo.product.Sku;
import com.itheima.product.pojo.product.SkuQuery;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {
	/**
	 * 通过商品id查询商品库存
	 * @param productId
	 * @return
	 */
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Override
	public List<Sku> findSkyByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			Long colorId = sku.getColorId();
			sku.setColor(colorDao.selectByPrimaryKey(colorId));
		}
		return skus;
	}
	/**
	 * 修改库存
	 */
	@Override
	public void updateById(Sku sku) {
		skuDao.updateByPrimaryKeySelective(sku);
	}
	

	

}
