package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.Product;
import com.skycloud.oa.utils.OaMsg;

public interface ProductService {
	/**
	 * 获取货品资料
	 * 
	 * @param pDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getProductList(ProductDto pDto, PageView pageView)
			throws OAException;

	/**
	 * 增加货品资料
	 * 
	 * @param product
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addProduct(Product product) throws OAException;

	/**
	 * 更新货品资料
	 * 
	 * @param product
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateProduct(Product product) throws OAException;

	/**
	 * 删除货品资料
	 * 
	 * @param pDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteProduct(String ids) throws OAException;
}
