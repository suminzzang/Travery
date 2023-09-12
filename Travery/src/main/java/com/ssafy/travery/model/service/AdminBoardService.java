package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.AdminBoardDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.util.PageNavigation;

public interface AdminBoardService {

	boolean writeAdminBoard(AdminBoardDto boardDto) throws Exception;
	List<AdminBoardDto> listAdminBoard(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	AdminBoardDto getAdminBoard(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyAdminBoard(AdminBoardDto boardDto) throws Exception;
	boolean deleteAdminBoard(int articleNo,String path) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
