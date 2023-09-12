package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.ssafy.travery.model.TripParameterDto;
import com.ssafy.travery.model.TripSearchDto;
import com.ssafy.travery.util.PageNavigation;

public interface TripSearchService {
	List<TripSearchDto> listArticle(TripParameterDto tripParameterDto) throws Exception;
	TripSearchDto getArticle(int contentId) throws Exception;
	Queue<TripSearchDto> distArticle(List<TripSearchDto> list) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	int getNum(TripParameterDto tripParameterDto) throws Exception;

}
