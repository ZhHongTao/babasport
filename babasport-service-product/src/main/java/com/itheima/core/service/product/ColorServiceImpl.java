package com.itheima.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.core.dao.product.product.ColorDao;
import com.itheima.product.pojo.product.Color;
import com.itheima.product.pojo.product.ColorQuery;

@Service("colorService")
@Transactional
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorDao colorDao;
	@Override
	public List<Color> findAllColor() {
		ColorQuery example = new ColorQuery();
		example.createCriteria().andParentIdNotEqualTo(0L);
		return colorDao.selectByExample(example);
	}

}
