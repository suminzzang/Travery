package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.QnABoardDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;

public interface QnABoardMapper {

	int writeQnABoard(QnABoardDto boardDto) throws SQLException;
	void registerFile(QnABoardDto boardDto) throws Exception;
	List<QnABoardDto> listQnABoard(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalQnABoardCount(Map<String, Object> map) throws SQLException;
	QnABoardDto getQnABoard(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyQnABoard(QnABoardDto boardDto) throws SQLException;
	int successQnABoard(QnABoardDto boardDto) throws SQLException;
	int deleteQnABoard(int articleNO) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
