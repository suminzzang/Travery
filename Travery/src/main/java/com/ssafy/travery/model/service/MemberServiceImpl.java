package com.ssafy.travery.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.model.MemberDto;
import com.ssafy.travery.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	
	public MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	@Override
	public MemberDto loginMember(Map<String, String> map) throws Exception {
		return memberMapper.loginMember(map);
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}
	
	@Override
	public int emailCheck(Map<String, String> map) throws Exception {
		return memberMapper.emailCheck(map);
	}
	
	@Override
	public int findPwEmailCheck(MemberDto memberDto) throws Exception {
		return memberMapper.findPwEmailCheck(memberDto);
	}

	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		return memberMapper.joinMember(memberDto);
	}

	@Override
	public boolean verifyMember(Map<String, String> map) throws Exception {
		return memberMapper.verifyMember(map) == 1;
	}
	
	@Override
	public String findId(Map<String, String> map) throws Exception {
		return memberMapper.findId(map);
	}
	
	@Override
	public int changePassword(MemberDto memberDto) throws Exception {
		return memberMapper.changePassword(memberDto);
	}

	@Override
	public MemberDto getInfo(String userId) throws Exception {
		return memberMapper.getInfo(userId);
	}

	@Override
	public int removeMember(Map<String, String> map) throws Exception {
		return memberMapper.removeMember(map);
	}

	@Override
	public int updateInfo(MemberDto updatememberDto) throws Exception {
		return memberMapper.updateInfo(updatememberDto);
	}

	@Override
	public List<HotPlaceBoardDto> getmyHotPlace(String userId) throws Exception {
		return memberMapper.getmyHotPlace(userId);
	}

	@Override
	public String getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public int saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		return memberMapper.saveRefreshToken(map);
	}

	@Override
	public int deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		return memberMapper.deleteRefreshToken(map);
	}

	@Override
	public String getSalt(String userId) throws Exception {
		return memberMapper.getSalt(userId);
	}

	@Override
	public void setSalt(Map<String, String> map) throws Exception {
		memberMapper.setSalt(map);
	}
}