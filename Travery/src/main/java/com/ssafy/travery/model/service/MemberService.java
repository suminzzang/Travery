package com.ssafy.travery.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.model.MemberDto;

public interface MemberService {

	public MemberDto loginMember(Map<String, String> map) throws Exception;
	int joinMember(MemberDto memberDto) throws Exception;
	boolean verifyMember(Map<String, String> map) throws Exception;
	int updateInfo(MemberDto updatememberDto) throws Exception;
	int removeMember(Map<String, String> map) throws Exception;
	MemberDto getInfo(String userId) throws Exception;
	int idCheck(String userId) throws Exception;
	int emailCheck(Map<String, String> map) throws Exception;
	int findPwEmailCheck(MemberDto memberDto) throws Exception;
	String findId(Map<String, String> map) throws Exception;
	int changePassword(MemberDto memberDto) throws Exception;
	List<HotPlaceBoardDto> getmyHotPlace(String userId)throws Exception;
	
	String getRefreshToken(String userId) throws Exception;
	int saveRefreshToken(String userId, String refreshToken) throws Exception;
	int deleteRefreshToken(String userId) throws Exception;
	String getSalt(String userId) throws Exception;
	void setSalt(Map<String, String> map) throws Exception;

}