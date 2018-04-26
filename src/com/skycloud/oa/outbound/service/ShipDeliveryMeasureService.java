package com.skycloud.oa.outbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>请用一句话概括功能</p>
 * @ClassName:ShipDeliveryMeasureService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年9月18日 上午11:20:40
 *
 */
public interface ShipDeliveryMeasureService 
{
	/**
	 * 
	 * @Title:ShipDeliveryMeasureService
	 * @Description:
	 * @param shipDeliverMeasureDto
	 * @param pageView
	 * @return
	 * @Date:2015年9月18日 上午11:20:46
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg list(ShipDeliverMeasureDto shipDeliverMeasureDto,PageView pageView) ;
	
	/**
	 * 
	 * @Title:ShipDeliveryMeasureService
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年9月18日 上午11:20:51
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg get(int id) ;
	
	/**
	 * 
	 * @Title:ShipDeliveryMeasureService
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年9月18日 上午11:20:57
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg delete(int id) ;
	
	/**
	 * 
	 * @Title:ShipDeliveryMeasureService
	 * @Description:
	 * @param shipDelvieryMeasure
	 * @return
	 * @Date:2015年9月18日 上午11:21:01
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg addorupdate(ShipDeliveryMeasure shipDelvieryMeasure) ;

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年9月18日上午10:00:55
	 * @throws
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request);
}
