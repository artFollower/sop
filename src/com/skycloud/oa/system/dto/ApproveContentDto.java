package com.skycloud.oa.system.dto;

public class ApproveContentDto {
	private String ids;//选择人的id组
	private String content;//意见
	private String url;//通知的url
	private String msgContent;//通知的信息
	private Integer typeId;//类型针对的id
	private Integer type;//类型  3.仓储费 首期费 4.仓储费 超期费5.账单
	private Integer typeStatus;//审批状态
	
	public ApproveContentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApproveContentDto(String ids, String content, String url,
			String msgContent, Integer typeId, Integer type, Integer typeStatus) {
		super();
		this.ids = ids;
		this.content = content;
		this.url = url;
		this.msgContent = msgContent;
		this.typeId = typeId;
		this.type = type;
		this.typeStatus = typeStatus;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}
	
}
