package com.ssafy.travery.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.UserBoardDto;
import com.ssafy.travery.model.mapper.UserBoardMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;

@Service
public class UserBoardServiceImpl implements UserBoardService {
	
	private UserBoardMapper userBoardMapper;
	
	public UserBoardServiceImpl(UserBoardMapper userBoardMapper) {
		this.userBoardMapper = userBoardMapper;
	}

	@Override
	public boolean writeUserBoard(UserBoardDto boardDto) throws Exception {
		int check = userBoardMapper.writeUserBoard(boardDto);
		return check == 1;
	}

	@Override
	public List<UserBoardDto> listUserBoard(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return userBoardMapper.listUserBoard(boardParameterDto);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = userBoardMapper.getTotalUserBoardCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public UserBoardDto getUserBoard(int articleNo) throws Exception {
		return userBoardMapper.getUserBoard(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		userBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyUserBoard(UserBoardDto boardDto) throws Exception {
		int check = userBoardMapper.modifyUserBoard(boardDto);
		return check == 1;
		
	}

	@Override
	public boolean deleteUserBoard(int articleNo,String path) throws Exception {
		int check = userBoardMapper.deleteUserBoard(articleNo);
		return check == 1;
	}
	
	@Override
	public int getNum(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = userBoardMapper.getTotalUserBoardCount(param);
		
		return totalCount;
	}
}
