package com.ssafy.travery.model;

public class HotPlacePhotoDto {
	private int articleNo;
	private int parentNo;
	private String photoUrl;
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public int getParentNo() {
		return parentNo;
	}
	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	@Override
	public String toString() {
		return "HotPlaceImageDto [articleNo=" + articleNo + ", parentNo=" + parentNo + ", photoUrl=" + photoUrl + "]";
	}
}
