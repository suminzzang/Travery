package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.QnABoardDto;
import com.ssafy.travery.util.PageNavigation;

public interface QnABoardService {

	boolean writeQnABoard(QnABoardDto boardDto) throws Exception;
	List<QnABoardDto> listQnABoard(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	QnABoardDto getQnABoard(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyQnABoard(QnABoardDto boardDto) throws Exception;
	boolean successQnABoard(QnABoardDto boardDto) throws Exception;
	boolean deleteQnABoard(int articleNo,String path) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
