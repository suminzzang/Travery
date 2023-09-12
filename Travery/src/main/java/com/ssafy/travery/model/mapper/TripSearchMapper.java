package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.TripParameterDto;
import com.ssafy.travery.model.TripSearchDto;

public interface TripSearchMapper {

	List<TripSearchDto> listArticle(TripParameterDto tripParameterDto) throws SQLException;
	TripSearchDto getArticle(int contentId) throws SQLException;
	int getTotalUserBoardCount(Map<String, Object> param) throws SQLException;
	int getTotalTripSearchCount(TripParameterDto tripParameterDto) throws SQLException;
	
}
