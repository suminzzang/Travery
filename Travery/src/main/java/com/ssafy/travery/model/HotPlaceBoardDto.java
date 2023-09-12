package com.ssafy.travery.model;

import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HotPlaceBoardDto : 핫플 정보", description = "핫플의 상세 정보를 나타낸다.")
public class HotPlaceBoardDto {
	
	@ApiModelProperty(value = "글번호")
	private int articleNo;
	@ApiModelProperty(value = "작성자 아이디")
	private String userId;
	@ApiModelProperty(value = "글제목")
	private String subject;
	@ApiModelProperty(value = "글내용")
	private String content;
	@ApiModelProperty(value = "조회수")
	private int hit;
	@ApiModelProperty(value = "작성일")
	private String registerTime;
	@ApiModelProperty(value = "사진url")
	private HotPlacePhotoDto[] photoUrl;
	@ApiModelProperty(value = "리스트 사진")
	private String firstImg;
	@ApiModelProperty(value = "관광지 id")
	private int contentId;
	@ApiModelProperty(value = "관광지 제목")
	private String title;
	@ApiModelProperty(value = "관광지 주소")
	private String addr1;
	@ApiModelProperty(value = "관광지 설명")
	private String overview;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	public String getFirstImg() {
		return firstImg;
	}
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}
	public HotPlacePhotoDto[] getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(HotPlacePhotoDto[] photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	@Override
	public String toString() {
		return "HotPlaceBoardDto [articleNo=" + articleNo + ", userId=" + userId + ", subject=" + subject + ", content="
				+ content + ", hit=" + hit + ", registerTime=" + registerTime + ", photoUrl="
				+ Arrays.toString(photoUrl) + ", firstImg=" + firstImg + ", contentId=" + contentId + ", title=" + title
				+ ", addr1=" + addr1 + ", overview=" + overview + "]";
	}

		
	
	
	
}
