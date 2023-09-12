package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.TripPlanBoardDto;
import com.ssafy.travery.util.PageNavigation;

public interface TripPlanBoardService {

	boolean writeTripPlanBoard(TripPlanBoardDto boardDto) throws Exception;
	List<TripPlanBoardDto> listTripPlanBoard(BoardParameterDto boardParameterDto) throws Exception;
	List<TripPlanBoardDto> monthlyTripPlanBoard(Map<String, Integer> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	TripPlanBoardDto getTripPlanBoard(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyTripPlanBoard(TripPlanBoardDto boardDto) throws Exception;
	boolean deleteTripPlanBoard(int articleNo,String path) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
