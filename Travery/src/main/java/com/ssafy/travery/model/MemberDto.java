package com.ssafy.travery.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class MemberDto {

	@ApiModelProperty(value = "회원 아이디")
	private String userId;
	@ApiModelProperty(value = "회원 이름")
	private String userName;
	@ApiModelProperty(value = "회원 비밀번호")
	private String userPwd;
	@ApiModelProperty(value = "회원 이메일 아이디")
	private String emailId;
	@ApiModelProperty(value = "회원 이메일 서버")
	private String emailDomain;
	@ApiModelProperty(value = "회원 전화번호")
	private String phoneNumber;
	@ApiModelProperty(value = "회원 시도 코드")
	private String userSido;
	@ApiModelProperty(value = "회원 구군 코드")
	private String userGugun;
	@ApiModelProperty(value = "회원가입일")
	private String joinDate;

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPwd() {
		return userPwd;
	}
	
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getEmailDomain() {
		return emailDomain;
	}
	
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getUserSido() {
		return userSido;
	}
	
	public void setUserSido(String userSido) {
		this.userSido = userSido;
	}
	
	public String getUserGugun() {
		return userGugun;
	}
	
	public void setUserGugun(String userGugun) {
		this.userGugun = userGugun;
	}
	
	public String getJoinDate() {
		return joinDate;
	}
	
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", emailId="
				+ emailId + ", emailDomain=" + emailDomain + ", phoneNumber=" + phoneNumber + ", userSido=" + userSido
				+ ", userGugun=" + userGugun + ", joinDate=" + joinDate + "]";
	}
}