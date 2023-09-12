package com.ssafy.travery.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MailCheckDto : 메일정보", description = "메일 인증을 위해 키와 인증 값을 임시로 저장한다.")
public class MailCheckDto {

	@ApiModelProperty(value = "인증이 유효한지 식별할 토큰")
	private String token;
	@ApiModelProperty(value = "전송하고 확인할 실제 인증 키")
	private String mailKey;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMailKey() {
		return mailKey;
	}
	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}
	
}
