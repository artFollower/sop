package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.Product;


public interface ProductDao{

	public List<Map<String,Object>> getProductList(ProductDto pDto,int start,int limit)throws OAException;
	public int getProductListCount(ProductDto pDto)throws OAException;
public void addProduct(Product product)throws OAException;
	
	public void updateProduct(Product product)throws OAException;
	
	public void deleteProduct(String ids)throws OAException;

}
