package com.skycloud.oa.inbound.dto;

import java.util.List;

import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.inbound.model.ExceedFeeLog;

public class ExceedFeeLogDto {
	@Translation(name = "超期费id")
	private Integer exceedId;//超期费id
	@Translation(name = "删除id")
    private String ids;//不在ids内的是超期费id的记录要删除掉
	@Translation(name = "超期记录列表")
    private List<ExceedFeeLog> efList;
    
	public ExceedFeeLogDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceedFeeLogDto(Integer exceedId, String ids, List<ExceedFeeLog> efList) {
		super();
		this.exceedId = exceedId;
		this.ids = ids;
		this.efList = efList;
	}

	public ExceedFeeLogDto(Integer exceedId, String ids) {
		super();
		this.exceedId = exceedId;
		this.ids = ids;
	}

	public Integer getExceedId() {
		return exceedId;
	}

	public void setExceedId(Integer exceedId) {
		this.exceedId = exceedId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<ExceedFeeLog> getEfList() {
		return efList;
	}

	public void setEfList(List<ExceedFeeLog> efList) {
		this.efList = efList;
	}

}
