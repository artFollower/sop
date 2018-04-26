package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Tube;
import com.skycloud.oa.utils.OaMsg;

/**
 * 泊位资料
 * 
 * @author jiahy
 *
 */
public interface TubeService {

	public OaMsg getTubeList(TubeDto tubeDto,PageView pageView)throws OAException;

	public OaMsg updateTube(Tube tube) throws OAException;

	public OaMsg addTube(Tube tube) throws OAException;

	public OaMsg deleteTube(TubeDto tubeDto) throws OAException;
}
