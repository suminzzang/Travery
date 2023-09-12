package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.AdminBoardDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;

public interface AdminBoardMapper {

	int writeAdminBoard(AdminBoardDto boardDto) throws SQLException;
	void registerFile(AdminBoardDto boardDto) throws Exception;
	List<AdminBoardDto> listAdminBoard(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalAdminBoardCount(Map<String, Object> map) throws SQLException;
	AdminBoardDto getAdminBoard(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyAdminBoard(AdminBoardDto boardDto) throws SQLException;
	int deleteAdminBoard(int articleNO) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
