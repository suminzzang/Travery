package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.QnABoardReplyDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;

public interface QnABoardReplyMapper {

	int writeQnABoardReply(QnABoardReplyDto boardDto) throws SQLException;
	void registerFile(QnABoardReplyDto boardDto) throws Exception;
	List<QnABoardReplyDto> listQnABoardReply(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalQnABoardReplyCount(Map<String, Object> map) throws SQLException;
	QnABoardReplyDto getQnABoardReply(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyQnABoardReply(QnABoardReplyDto boardDto) throws SQLException;
	int successQnABoardReply(QnABoardReplyDto boardDto) throws SQLException;
	int deleteQnABoardReply(int articleNO) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
