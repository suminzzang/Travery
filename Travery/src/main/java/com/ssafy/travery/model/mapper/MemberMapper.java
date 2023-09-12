package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.model.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto loginMember(Map<String, String> map) throws SQLException;
	int joinMember(MemberDto memberDto) throws SQLException;
	int verifyMember(Map<String, String> map) throws SQLException;
	int updateInfo(MemberDto updatememberDto) throws SQLException;
	int removeMember(Map<String, String> map) throws SQLException;
	MemberDto getInfo(String userId) throws SQLException;
	int idCheck(String userId) throws SQLException;
	int emailCheck(Map<String, String> map) throws SQLException;
	int findPwEmailCheck(MemberDto memberDto) throws SQLException;
	String findId(Map<String, String> map) throws SQLException;
	int changePassword(MemberDto memberDto) throws SQLException;
	List<HotPlaceBoardDto> getmyHotPlace(String userId) throws SQLException;
	
	String getRefreshToken(String userId) throws SQLException;
	int saveRefreshToken(Map<String, String> map) throws SQLException;
	int deleteRefreshToken(Map<String, String> map) throws SQLException;
	String getSalt(String userId) throws SQLException;
	void setSalt(Map<String, String> map) throws SQLException;

}