package com.jason.enumeration;

public enum ErrorMsgEnum {
	
	SUCCESS(true, 2000,"正常返回", "操作成功"), 
	
	// 系统错误，50开头
	SYS_ERROR(false, 5000, "系统错误", "系统错误"),
	PARAM_INVILAD(false, 5001, "参数出现异常", "参数出现异常"), 
	DATA_NO_COMPLETE(false, 5002, "数据填写不完整，请检查", "数据填写不完整，请检查");

	private ErrorMsgEnum(boolean ok, int code, String msg ,String userMsg) {
		this.ok = ok;
		this.code = code;
		this.msg = msg;
		this.userMsg = userMsg;
	}

	private boolean ok;
	private int code;
	private String msg;
	private String userMsg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getUserMsg() {
		return userMsg;
	}

	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	
	

}
