package com.skycloud.oa.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dao.IntentionDao;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.order.service.IntentionService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.OaMsg;

/**
 * @author yanyufeng
 *
 */
@Service
public class IntentionServiceImpl implements IntentionService {

	private static Logger LOG = Logger.getLogger(IntentionServiceImpl.class);
	
	@Autowired
	private IntentionDao intentionDao;
	
	/**
	 * 增加订单意向
	 * @param intention
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INTENTION,type=C.LOG_TYPE.CREATE)
	public OaMsg addIntention(Intention intention) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("生成code");
			String time = DateTimeUtil.getString(new Date(System.currentTimeMillis()), DateTimeUtil
					.getShortDateTimeFormatText());
			String code = "YX" + time.substring(2, 4) + "YZ";
			String[] type = new String[] { "A", "B", "C", "D","E" };
			code += type[intention.getType() - 1];
			int codecount=intentionDao.getTheSameIntention(code);
			String codeNumber = "";
			if (codecount < 9) {
				codeNumber = "00" + (codecount + 1);
			} else if (codecount >= 9 && codecount < 99) {
				codeNumber = "0" + (codecount + 1);
			} else {
				codeNumber = (codecount + 1) + "";
			}
			code += codeNumber;
			intention.setCode(code);
			LOG.debug("code="+code);
			LOG.debug("开始插入订单意向");
			int id=intentionDao.addIntention(intention);
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			LOG.debug("插入成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("插入成功");
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}

	/**
	 * 删除订单意向
	 * @param intentionId
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INTENTION,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteIntention(String intentionId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除订单意向");
			intentionDao.deleteIntention(intentionId);
			LOG.debug("删除成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
		return oaMsg;
	}

	/**
	 * 修改订单意向
	 * @param intention
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INTENTION,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateIntention(Intention intention,String mEditTime) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			IntentionDto dto=new IntentionDto();
			dto.setId(intention.getId());
			Map<String,Object> info=intentionDao.getIntentionListByCondition(dto, 0, 0).get(0);
			if(!Common.empty(mEditTime)){
				if(!mEditTime.equals(info.get("editTime").toString())){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					return oaMsg;
				}
				
			}
			
			LOG.debug("开始修改订单意向");
			intentionDao.updateIntention(intention);
			LOG.debug("修改成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("修改成功");
		} catch (RuntimeException re) {
			LOG.error("修改失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "修改失败",re);
		}
		return oaMsg;
	}

	/**
	 * 多条件查询订单意向
	 * @param intentionDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	public OaMsg getIntentionListByCondition(IntentionDto intentionDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询订单意向");
			List<Map<String,Object>> intentionList=intentionDao.getIntentionListByCondition(intentionDto, pageView.getStartRecord(), pageView.getMaxresult());
			count=intentionDao.getIntentionListCountByCondition(intentionDto);
//			pageView.setTotalrecord(count);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(intentionList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	


}
