package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;
import com.ssafy.travery.model.UserBoardDto;

public interface UserBoardMapper {

	int writeUserBoard(UserBoardDto boardDto) throws SQLException;
	void registerFile(UserBoardDto boardDto) throws Exception;
	List<UserBoardDto> listUserBoard(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalUserBoardCount(Map<String, Object> param) throws SQLException;
	UserBoardDto getUserBoard(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyUserBoard(UserBoardDto boardDto) throws SQLException;
	int deleteUserBoard(int articleNO) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
