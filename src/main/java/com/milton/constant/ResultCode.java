package com.milton.constant;

/**
 * @author milton.zhang
 * @description 返回错误码定义
 * @date 2017-06-05
 */
public enum ResultCode {
	//公共错误码
	SUCCESS(0, "成功"),
	ERR_TYPE(9993, "类型错误"),
	ERR_TOKEN_TIMEOUT(9994, "登录令牌超时"),
	ERR_NO_PERMISSION(9995, "无操作权限"),
	ERR_NO_RECORD(9996, "无此记录"),
	ERR_OPERATE_NOT_SUPPORT(9997, "不支持的操作"),
	ERR_PARAMS_ERR(9998, "参数不能为空"),
	ERR_UNKNOWN(9999, "未知错误"),

	//用户相关错误码
	ERR_ACCOUNT_PWD(1000, "用户名或密码错误"),
	ERR_NO_LOGIN(1001, "请登录"),

	//公共业务相关错误码
	ERR_FILE_EMPTY(2000, "文件不能为空"),
	ERR_FILE_TYPE_EMPTY(2001, "文件业务类型不能为空");

	private Integer code;
	private String desc;

	ResultCode(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}