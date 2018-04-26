/**
 * 
 */
package com.skycloud.oa.feebill.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *账单接口
 * @author jiahy
 * @version 2015年6月14日 下午6:04:16
 */
public interface FeeBillService {

	/**
	 * 获取账单列表
	 *@author jiahy
	 * @param feeBillDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public OaMsg getFeeBillList(FeeBillDto feeBillDto ,PageView pageView) throws OAException;
	/**
	 * 添加账单
	 *@author jiahy
	 * @param feeBill
	 * @return
	 * @throws OAException
	 */
	public OaMsg addFeeBill(FeeBill feeBill) throws OAException;
	/**
	 * 更新账单
	 *@author jiahy
	 * @param feeBill
	 * @return
	 * @throws OAException
	 */
	public OaMsg updateFeeBill(FeeBill feeBill) throws OAException;
	/**
	 * 删除账单
	 *@author jiahy
	 * @param feeBillDto
	 * @return
	 * @throws OAException
	 */
	public OaMsg deleteFeeBill(FeeBillDto feeBillDto) throws OAException;
	/**
	 * 获取账单号
	 *@author jiahy
	 * @param feeBillDto
	 * @return
	 */
	public OaMsg getCodeNum(FeeBillDto feeBillDto) throws OAException;
	/**
	 * 获取统计账单金额
	 *@author jiahy
	 * @param feeBillDto
	 * @return
	 */
	public OaMsg getTotalFee(FeeBillDto feeBillDto) throws OAException;
	/**
	 * @Title rollback
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年3月18日上午9:47:12
	 * @throws
	 */
	public OaMsg rollback(FeeBillDto feeBillDto)throws OAException;
	/**
	 * @Title exportDetailFee
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param feeBillDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年7月26日下午3:31:19
	 * @throws
	 */
	public HSSFWorkbook exportDetailFee(HttpServletRequest request, FeeBillDto feeBillDto) throws OAException;
}
