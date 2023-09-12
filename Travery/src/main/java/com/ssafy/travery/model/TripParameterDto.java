package com.ssafy.travery.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BoardParameterDto : 게시판 파라미터 정보", description = "게시판의 글을 얻기위한 부가적인 파라미터정보.")
public class TripParameterDto {

	@ApiModelProperty(value = "현재 페이지 번호")
	private int pg;
	@ApiModelProperty(value = "페이지당 글갯수")
	private int spp;
	@ApiModelProperty(value = "페이지의 시작 글번호")
	private int start;
	@ApiModelProperty(value = "검색 조건- 지역/시도")
	private int sido_code;
	@ApiModelProperty(value = "검색 조건- 지역/구군")
	private int gugun_code;
	@ApiModelProperty(value = "검색 조건- 관광 유형")
	private int contentTypeId;
	@ApiModelProperty(value = "검색 조건- 정렬 기준")
	private int sortType;
	@ApiModelProperty(value = "정렬 조건- 현재 위도")
	private double latitude;
	@ApiModelProperty(value = "정렬 조건- 현개 경도")
	private double longitude;
	@ApiModelProperty(value = "검색어")
	private String word;
	
	public TripParameterDto() {
		pg = 1;
		spp = 10;
		sortType = 1;
		latitude = 1;
		longitude = 1;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSido_code() {
		return sido_code;
	}

	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}

	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	

	public int getGugun_code() {
		return gugun_code;
	}

	public void setGugun_code(int gugun_code) {
		this.gugun_code = gugun_code;
	}

	@Override
	public String toString() {
		return "TripParameterDto [pg=" + pg + ", spp=" + spp + ", start=" + start + ", sido_code=" + sido_code
				+ ", contentTypeId=" + contentTypeId + ", sortType=" + sortType + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", word=" + word + "]";
	}
	
	

}

