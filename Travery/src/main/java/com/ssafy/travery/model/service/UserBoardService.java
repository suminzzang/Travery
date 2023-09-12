package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.UserBoardDto;
import com.ssafy.travery.util.PageNavigation;

public interface UserBoardService {

	boolean writeUserBoard(UserBoardDto boardDto) throws Exception;
	List<UserBoardDto> listUserBoard(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	UserBoardDto getUserBoard(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyUserBoard(UserBoardDto boardDto) throws Exception;
	boolean deleteUserBoard(int articleNo,String path) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
