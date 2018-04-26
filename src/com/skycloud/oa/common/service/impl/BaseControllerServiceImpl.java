package com.skycloud.oa.common.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.common.service.BaseControllerService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.OaMsg;

@Service
public class BaseControllerServiceImpl implements BaseControllerService {

	private static Logger LOG = Logger.getLogger(BaseControllerServiceImpl.class);

	@Autowired
	BaseControllerDao baseControllerDao;

	@Override
	public OaMsg getTankName(String tankName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getTankName(tankName));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getUser() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getUser());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTrans(String arrivalId) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getTrans(arrivalId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getWeather() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getWeather());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getWindDirection() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getWindDirection());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getWindPower() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getWindPower());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getShipName(String shipName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getShipName(shipName));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCanMakeInvoiceShipName(String productId, String ladingCode,Integer isNoTransport) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getCanMakeInvoiceShipName(productId, ladingCode,isNoTransport));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCanMakeInvoiceTruck() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getCanMakeInvoiceTruck());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getParkList(String parkName, String ids) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getParkList(parkName, ids));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getBerthByName(String berthName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getBerthByName(berthName));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getVehiclePlateByTrainId(String trainId) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getVehiclePlateByTrainId(trainId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getParkName(String parkName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getParkName(parkName));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getVehiclePlateList(String vehiclePlate) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getVehiclePlateList(vehiclePlate));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalShipInfo() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getArrivalShipInfo());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getLadingCodeList(String id, String code, String productId) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getLadingCodeList(id, code, productId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getShipChineseName(String shipId) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getShipChineseName(shipId));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getClientNameList(String clientName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getClientNameList(clientName));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getHistoryClientName() {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getHistoryClientName());
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getClientNameByProductId(String clientName, String productId) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getClientNameByProductId(clientName, productId));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getProductNameList(String productName) {

		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getProductNameList(productName));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTankCode(Integer productId) {
		OaMsg oaMsg = new OaMsg();
		try {

			oaMsg.getData().addAll(baseControllerDao.getTankCode(productId));
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);

		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getCargoAgentList(CargoAgentDto caDto, int start, int limit) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(baseControllerDao.getCargoAgentList(caDto, start, limit));
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	/**
	 * 查询流量计台帐中船名
	 * 
	 * @Title:queryShipInfo
	 * @Description:
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg queryShipInfo() throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.getData().addAll(baseControllerDao.queryShipInfo());

		return oaMsg;
	}

	/**
	 * 系统管理-基础信息-客户资料-添加客户编号总数
	 * 
	 * @Title:queryClientCount
	 * @Description:
	 * @param queryStr
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg queryClientCount(String queryStr) throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.getData().addAll(baseControllerDao.queryClientCount(queryStr));

		return oaMsg;
	}

	/**
	 * 查询某辆车的核载量
	 * 
	 * @Title:queryOneCar
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg queryOneCar(String carNo) throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.getData().addAll(baseControllerDao.queryOneCar(carNo));

		return oaMsg;
	}

	/**
	 * 更新某辆车的核载量
	 * 
	 * @Title:updateOneCar
	 * @Description:
	 * @param carNo
	 * @param carAmount
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	@Log(object = C.LOG_OBJECT.PCS_TRUCK, type = C.LOG_TYPE.UPDATE)
	public OaMsg updateOneCar(Truck truck) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			baseControllerDao.updateOneCar(truck);
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}

		return oaMsg;
	}

	@Override
	public OaMsg getCargoList() throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(baseControllerDao.getCargoList());
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
}
