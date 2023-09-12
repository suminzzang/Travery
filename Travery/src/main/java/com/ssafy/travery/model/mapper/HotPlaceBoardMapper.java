package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.model.HotPlacePhotoDto;


public interface HotPlaceBoardMapper {

	int writeHotPlaceBoard(HotPlaceBoardDto hotplaceboardDto) throws SQLException;

	List<HotPlaceBoardDto> listHotPlaceBoard(BoardParameterDto boardParameterDto) throws SQLException;
	
	List<HotPlaceBoardDto> monthlyHotPlaceBoard(Map<String, Integer> map) throws SQLException;
	
	int getTotalHotPlaceBoardCount(Map<String, Object> map) throws SQLException;

	HotPlaceBoardDto getHotPlaceBoard(int articleNo)throws SQLException;

	void updateHit(int articleNo)throws SQLException;

	int modifyHotPlaceBoard(HotPlaceBoardDto boardDto)throws SQLException;

	int deleteHotPlaceBoard(int articleNo)throws SQLException;
	
	List<HotPlacePhotoDto> listDetail(int articleNo) throws SQLException;
	

	
}
