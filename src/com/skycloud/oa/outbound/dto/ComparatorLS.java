package com.skycloud.oa.outbound.dto;

import java.util.Comparator;
public class ComparatorLS implements Comparator<GoodsLSDto> {

	@Override
	public int compare(GoodsLSDto o1, GoodsLSDto o2) {
		// TODO Auto-generated method stub
		int flag=(int) (o1.getTime()+"".compareTo(o2.getTime()+""));
		return flag;
	}

}
