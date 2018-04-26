package com.skycloud.oa.esb.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.esb.dao.EsbShipDao;
import com.skycloud.oa.esb.dao.HarborBillDao;
import com.skycloud.oa.esb.dao.HarborContainerDamageDao;
import com.skycloud.oa.esb.dao.HarborContainerDao;
import com.skycloud.oa.esb.dao.HarborContainerSummaryDao;
import com.skycloud.oa.esb.dao.HarborDao;
import com.skycloud.oa.esb.dao.HarborShipDao;
import com.skycloud.oa.esb.dao.MessageHeadDao;
import com.skycloud.oa.esb.dto.BulkDto;
import com.skycloud.oa.esb.dto.HarborShipDto;
import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.esb.model.Harbor;
import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.esb.service.HarborShipService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.FileHelper;
import com.skycloud.oa.utils.OaMsg;

/**
 * 港务船业务逻辑处理接口实现类
 * @ClassName: HarborShipServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午5:37:49
 */
@Service
public class HarborShipServiceImpl implements HarborShipService {

	private static Logger LOG = Logger.getLogger(HarborShipServiceImpl.class);
	
	@Autowired
	private HarborShipDao harborShipDao;
	@Autowired
	private MessageHeadDao messageHeadDao;
	@Autowired
	private HarborDao harborDao;
	@Autowired
	private HarborBillDao harborBillDao;
	@Autowired
	private HarborContainerDao harborContainerDao;
	@Autowired
	private HarborContainerDamageDao harborContainerDamageDao;
	@Autowired
	private HarborContainerSummaryDao harborContainerSummaryDao;
	@Autowired
	private EsbShipDao esbShipDao;

	@Override
	@Log(object=C.LOG_OBJECT.ESB_HARBOR,type=C.LOG_TYPE.CREATE)
	public void create(HarborShipDto harborShipDto,
			OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			HarborShip harborShip = harborShipDto.getHarborShip();
			harborShip.setId(harborShipDao.create(harborShip));//保存船信息
			
			MessageHead messageHead = harborShipDto.getMessageHead();
			messageHead.setShip(harborShip);
			messageHead.setId(messageHeadDao.create(messageHead));//保存报文头信息
			if(harborShip.getCatogary().equals("BULK")) {
				List<BulkDto> discharges = harborShipDto.getDischarges();
				for(BulkDto discharge : discharges) {
					Harbor harbor = discharge.getHarbor();
					
					HarborBill harborBill = discharge.getHarborBill();
					harborBill.setShip(harborShip);
					harborBill.setId(harborBillDao.create(harborBill));//保存港务提单
					
					harbor.setObjectId(harborBill.getId());
					harbor.setCatogary("BULK");
					harbor.setId(harborDao.create(harbor));
				}
				
			}else {
				
			}
			msg.setMsg("添加成功");
			LOG.debug("添加成功");
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加港务信息失败", e);
		}
	}

	@Override
	public void get(HarborShip harborShip, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(harborShipDao.get(harborShip, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, harborShipDao.count(harborShip) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("港务信息查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_SHIP,type=C.LOG_TYPE.UPDATE)
	public void update(HarborShip harborShip, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			if (harborShipDao.modify(harborShip)) {
				msg.setMsg("修改成功");
				LOG.debug("港务信息修改"+ new Gson().toJson(harborShip));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("修改数据的不存在");
				LOG.debug("要修改的不存在:" +  new Gson().toJson(harborShip));
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_SHIP,type=C.LOG_TYPE.DELETE)
	public void delete(String ids,String catogary, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(harborShipDao.delete(ids,catogary)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除港务信息"+ids);
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的对象不存在");
				LOG.debug("删除的港务信息不存在:"+ids);
			}
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_HARBOR,type=C.LOG_TYPE.UPLOAD)
	public void upload(HarborShip harborShip, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			Gson gson = new Gson();

			List<Map<String, Object>> ship = harborShipDao.get(harborShip, 0, 0);// 查询船信息
			harborShip = gson.fromJson(gson.toJson(ship.get(0)), HarborShip.class);
			
			try{
				EsbShip es = new EsbShip();
				es.setEnglishName(harborShip.getVesselCode());
				List<Map<String, Object>> eship = esbShipDao.get(es, 0, 0);
				harborShip.setVesselCode(eship.get(0).get("code").toString());
			}catch(Exception e) {
				
			}
			
			String path = harborShip.getFile();
			//未发送的报文先将报文保存到本地后再上传上去
			if (Common.empty(harborShip.getStatus()) || harborShip.getStatus().equals("0")) {
				MessageHead mh = new MessageHead();
				mh.setShip(harborShip);

				HarborBill hb = new HarborBill();
				hb.setShip(harborShip);

				List<Map<String, Object>> head = messageHeadDao.get(mh, 0, 0);// 查询报文头
				mh = gson.fromJson(gson.toJson(head.get(0)), MessageHead.class);

				List<Map<String, Object>> bill = harborBillDao.get(hb, 0, 0);

				// 写文件
				Calendar calendar = Calendar.getInstance();
				//文件先保存到本地，每天建一个文件夹，文件名以yzpc+时间戳
				path = Global.cloudConfig.get("esb.message.path").toString() + "/" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DAY_OF_MONTH);
				String file = "/YZPC" + System.currentTimeMillis() + ".txt";
				FileHelper.createDir(path);
				path = path + "/" + file;
				FileHelper.writeFile(path, mh.toString().replaceAll(":null", ":") + "\n", false);
				FileHelper.writeFile(path, harborShip.toString().replaceAll(":null", ":") + "\n", true);

				for (Map<String, Object> map : bill) {
					BulkDto dd = gson.fromJson(gson.toJson(map), BulkDto.class);
					FileHelper.writeFile(path, dd.getHarborBill().toString().replaceAll(":null", ":") + "\n", true);
					FileHelper.writeFile(path, dd.getHarbor().toBulkString().replaceAll(":null", ":") + "\n", true);
				}
				FileHelper.writeFile(path, "99:" + (bill.size() * 2 + 3) + "'\n", true);
				LOG.info("已生产报文:"+path);
			}else if(harborShip.getStatus().equals("1")){
				msg.setMsg("报文已上传成功");
				return;
			}
			String remoteResult = FileHelper.remote(path);
			harborShip.setFile(path);
			if (remoteResult.equals(Constant.SYS_CODE_SUCCESS)) {
				harborShip.setStatus("1");
			} else {
				harborShip.setStatus("2");
				msg.setCode(remoteResult);
				msg.setMsg("报文上传失败");
			}
			try {
				if(!harborShipDao.modify(harborShip)) {
					
				}
			}catch(Exception e) {
				LOG.error(e.getMessage(), e);
			}
			msg.setMsg("报文上传成功");
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}	
}
