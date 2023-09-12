package com.ssafy.travery.model.service;


import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.util.PageNavigation;

public interface HotPlaceBoardService {

	boolean writeHotPlaceBoard(HotPlaceBoardDto hotplaceBoard) throws Exception;
	List<HotPlaceBoardDto> listHotPlaceBoard(BoardParameterDto boardParameterDto) throws Exception;
	List<HotPlaceBoardDto> monthlyHotPlaceBoard(Map<String, Integer> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	HotPlaceBoardDto getHotPlaceBoard(int articleNo)throws Exception;
	void updateHit(int articleNo)throws Exception;

	boolean modifyHotPlaceBoard(HotPlaceBoardDto boardDto)throws Exception;
	boolean deleteHotPlaceBoard(int articleNo)throws Exception;
	int getNum(Map<String,String> map) throws Exception;
}


