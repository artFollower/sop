package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.GuestDto;
import com.skycloud.oa.system.model.Guest;

public interface GuestDao{

	public List<Map<String,Object>> getGuestList(GuestDto gDto,int start,int limit)throws OAException;
	
	public void addGuest(Guest guest)throws OAException;

	public void updateGuest(Guest guest)throws OAException;

	public void deleteGuest(String ids)throws OAException;

}
