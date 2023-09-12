package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.QnABoardReplyDto;
import com.ssafy.travery.util.PageNavigation;

public interface QnABoardReplyService {

	boolean writeQnABoardReply(QnABoardReplyDto boardDto) throws Exception;
	List<QnABoardReplyDto> listQnABoardReply(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	QnABoardReplyDto getQnABoardReply(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	boolean modifyQnABoardReply(QnABoardReplyDto boardDto) throws Exception;
	boolean successQnABoardReply(QnABoardReplyDto boardDto) throws Exception;
	boolean deleteQnABoardReply(int articleNo,String path) throws Exception;
	int getNum(Map<String,String> map) throws Exception;
	
}
