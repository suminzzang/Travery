package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;
import com.ssafy.travery.model.TripDetailDto;
import com.ssafy.travery.model.TripPlanBoardDto;

public interface TripPlanBoardMapper {

	int writeTripPlanBoard(TripPlanBoardDto boardDto) throws SQLException;
	void registerFile(TripPlanBoardDto boardDto) throws Exception;
	List<TripPlanBoardDto> listTripPlanBoard(BoardParameterDto boardParameterDto) throws SQLException;
	List<TripPlanBoardDto> monthlyTripPlanBoard(Map<String, Integer> map) throws SQLException;
	int getTotalTripPlanBoardCount(Map<String, Object> param) throws SQLException;
	TripPlanBoardDto getTripPlanBoard(int articleNo) throws SQLException;
	List<TripDetailDto> listDetail(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyTripPlanBoard(TripPlanBoardDto boardDto) throws SQLException;
	int deleteTripPlanBoard(int articleNO) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
