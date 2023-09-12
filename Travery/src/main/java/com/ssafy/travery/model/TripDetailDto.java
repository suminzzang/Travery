package com.ssafy.travery.model;

public class TripDetailDto {
	int articleNo;
	int parentNo;
	int contentId;
	String title;
	String addr1;
	String overview;
	String content;
	String detailStartDate;
	String detailEndDate;
	
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetailStartDate() {
		return detailStartDate;
	}
	public void setDetailStartDate(String detailStartDate) {
		this.detailStartDate = detailStartDate;
	}
	public String getDetailEndDate() {
		return detailEndDate;
	}
	public void setDetailEndDate(String detailEndDate) {
		this.detailEndDate = detailEndDate;
	}
	
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
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
		return "TripDetailDto [contentId=" + contentId + ", articleNo=" + articleNo + ", title=" + title + ", addr1="
				+ addr1 + ", overview=" + overview + ", content=" + content + ", detailStartDate=" + detailStartDate
				+ ", detailEndDate=" + detailEndDate + ", parentNo=" + parentNo + "]";
	}

	

	
	
}
