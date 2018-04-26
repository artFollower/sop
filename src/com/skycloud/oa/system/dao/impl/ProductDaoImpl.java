package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ProductDao;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.Product;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>系统管理-基础信息-货品资料</p>
 * @ClassName:ProductDaoImpl
 * @Description:
 * @Author:
 * @Date:2015年8月7日 下午3:01:27
 *
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao 
{
	/**
	 * 记录日志
	 */
	private static Logger LOG = Logger.getLogger(ProductDaoImpl.class);
	
	/**
	 * 系统管理-基础信息-货品资料-查询货品列表
	 * @Title:getProductList
	 * @Description:
	 * @param pDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getProductList(ProductDto pDto,int start,int limit) throws OAException 
	{
		try 
		{
			
			String sql="";
			if(!Common.empty(pDto.getLetter())&&!("all").equals(pDto.getLetter())){
				 sql = "select a.*,s.name editUserName,b.value value from t_pcs_product a left join t_pcs_product_type b on a.type=b.key LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.sysStatus) ";
				
				String up=pDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+pDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName,b.value value from t_pcs_product a left join t_pcs_product_type b on a.type=b.key LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.sysStatus) ";
			}
			
			
//			String sql="select a.*,b.value value from t_pcs_product a left join t_pcs_product_type b on a.type=b.key where 1=1 ";
			if(!Common.empty(pDto.getId()))
			{
				sql+="and a.id="+pDto.getId();
			}
			if(pDto.getOils()!=null){
				sql+=" and a.oils="+pDto.getOils();
			}
			if(!Common.empty(pDto.getCode()))
			{
				sql+=" and (a.code like '%"+pDto.getCode()+"%'  or a.name like '%"+pDto.getName()+"%' or a.value like '%"+pDto.getValue()+"%') ";
			}
			sql+=" ORDER BY a.status DESC ";
			if(limit!=0)
			{
				sql+=" limit "+start+" , "+limit;
			}
			
			return executeQuery(sql);
		} 
		catch (RuntimeException e) 
		{
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

	/**
	 * 系统管理-基础信息-货品资料-查询货品列表总记录数
	 * @Title:getProductListCount
	 * @Description:
	 * @param pDto
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public int getProductListCount(ProductDto pDto) throws OAException 
	{
		try 
		{
			
			String sql="";
			if(!Common.empty(pDto.getLetter())&&!("all").equals(pDto.getLetter())){
				 sql = "select count(*) from t_pcs_product a left join t_pcs_product_type b on a.type=b.key,t_sys_pinyin c where 1=1 and isnull(a.sysStatus) ";
				
				String up=pDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+pDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*) from t_pcs_product a left join t_pcs_product_type b on a.type=b.key where 1=1 and isnull(a.sysStatus) ";
			}
			
//			String sql="select count(*) from t_pcs_product a left join t_pcs_product_type b on a.type=b.key where 1=1 ";
			if(!Common.empty(pDto.getId()))
			{
				sql+="and id="+pDto.getId();
			}
			if(!Common.empty(pDto.getCode()))
			{
				sql+=" and (code= '"+pDto.getCode()+"'  or name= '"+pDto.getName()+"' or value like '"+pDto.getValue()+"')";
			}
			
			return (int) getCount(sql);
		} 
		catch (RuntimeException e) 
		{
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}
	
	/**
	 * 系统管理-基础信息-货品资料-添加货品
	 * @Title:addProduct
	 * @Description:
	 * @param product
	 * @throws OAException
	 * @see
	 */
	@Override
	public void addProduct(Product product) throws OAException 
	{
		try 
		{
			save(product);
		} 
		catch (RuntimeException e) 
		{
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

	/**
	 * 系统管理-基础信息-货品资料-修改货品
	 * @Title:updateProduct
	 * @Description:
	 * @param product
	 * @throws OAException
	 * @see
	 */
	@Override
	public void updateProduct(Product product) throws OAException 
	{
		try {
			String sql="update t_pcs_product set id=id";
		      if(null!=product.getCode()){
		    	  sql+=" ,code="+"'"+product.getCode()+"'";
		      }
		      if(null!=product.getName()){
		    	  sql+=" ,name="+"'"+product.getName()+"'";
		      }
		      if(null!=product.getType()){
		    	 sql+=" ,type="+product.getType();
		      }
		      if(null!=product.getType()){
			    	 sql+=" ,type="+product.getType();
			      }
		      if(null!=product.getOils()){
			    	 sql+=" ,oils="+product.getOils();
			      }
		      
		      if(null!=product.getStandardDensity()){
			    	 sql+=" ,standardDensity='"+product.getStandardDensity()+"'";
			      }
		      
		      if(null!=product.getVolumeRatio()){
			    	 sql+=" ,volumeRatio='"+product.getVolumeRatio()+"'";
			      }
		      
		      if(null!=product.getDescription()){
			    	 sql+=" ,description='"+product.getDescription()+"'";
			      }
		      if(null!=product.getStatus()){
			    	 sql+=" ,status="+product.getStatus();
			      }
		      if(null!=product.getEditUserId()){
			    	 sql+=" ,editUserId='"+product.getEditUserId()+"'";
			      }
				sql+=" where id="+product.getId();
			   executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新储罐失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新储罐失败",e);
		}		
			}

	/**
	 * 系统管理-基础信息-货品资料-删除货品
	 * @Title:deleteProduct
	 * @Description:
	 * @param ids
	 * @throws OAException
	 * @see
	 */
	@Override
	public void deleteProduct(String ids) throws OAException 
	{
		try 
		{
			String sql="update t_pcs_product set sysStatus=1 where id in ("+ids+")";
			execute(sql);	
		} 
		catch (RuntimeException e) 
		{
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}

}