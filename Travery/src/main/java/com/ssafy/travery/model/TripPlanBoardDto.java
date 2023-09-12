package com.ssafy.travery.model;

import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TripPlanBoardDto : 여행계획 정보", description = "여행계획의 상세 정보를 나타낸다.")
public class TripPlanBoardDto {
	
	@ApiModelProperty(value = "글번호")
	private int articleNo;
	@ApiModelProperty(value = "작성자 아이디")
	private String userId;
	@ApiModelProperty(value = "글제목")
	private String subject;
	@ApiModelProperty(value = "여행 계획")
	private TripDetailDto[] detailPlan;
	@ApiModelProperty(value = "여행 시작날짜")
	private String startDate;
	@ApiModelProperty(value = "여행 종료날짜")
	private String endDate;
	@ApiModelProperty(value = "조회수")
	private int hit;
	@ApiModelProperty(value = "작성일")
	private String registerTime;
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public TripDetailDto[] getDetailPlan() {
		return detailPlan;
	}
	public void setDetailPlan(TripDetailDto[] detailPlan) {
		this.detailPlan = detailPlan;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "TripPlanBoardDto [articleNo=" + articleNo + ", userId=" + userId + ", subject=" + subject
				+ ", detailPlan=" + Arrays.toString(detailPlan) + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", hit=" + hit + ", registerTime=" + registerTime + "]";
	}
	
	
}
