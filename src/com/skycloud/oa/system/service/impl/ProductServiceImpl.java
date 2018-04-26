package com.skycloud.oa.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.ProductDao;
import com.skycloud.oa.system.dto.ProductDto;
import com.skycloud.oa.system.model.Product;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ProductServiceImpl implements ProductService {
	private static Logger LOG = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getProductList(ProductDto pDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(
					productDao.getProductList(pDto, pageView.getStartRecord(), pageView.getMaxresult()));
			int count=productDao.getProductListCount(pDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PRODUCT,type=C.LOG_TYPE.CREATE)
	public OaMsg addProduct(Product product) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			product.setEditUserId(user.getId()) ;
			productDao.addProduct(product);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PRODUCT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateProduct(Product product) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			product.setEditUserId(user.getId()) ;
			productDao.updateProduct(product);
			baseControllerDao.sendSystemMessage("货品", user.getId(),"BASEINFO",2) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PRODUCT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteProduct(String product) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			productDao.deleteProduct(product);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
