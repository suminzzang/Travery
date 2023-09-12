package com.ssafy.travery.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.travery.model.AdminBoardDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.mapper.AdminBoardMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;


@Service
public class AdminBoardServiceImpl implements AdminBoardService {
	
	private AdminBoardMapper adminBoardMapper;
	
	public AdminBoardServiceImpl(AdminBoardMapper adminBoardMapper) {
		this.adminBoardMapper = adminBoardMapper;
	}

	@Override
	public boolean writeAdminBoard(AdminBoardDto boardDto) throws Exception {
		int check = adminBoardMapper.writeAdminBoard(boardDto);
		return check ==1;
	}

	@Override
	public List<AdminBoardDto> listAdminBoard(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return adminBoardMapper.listAdminBoard(boardParameterDto);
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
		int totalCount = adminBoardMapper.getTotalAdminBoardCount(param);
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
	public AdminBoardDto getAdminBoard(int articleNo) throws Exception {
		return adminBoardMapper.getAdminBoard(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		adminBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyAdminBoard(AdminBoardDto boardDto) throws Exception {
		int check = adminBoardMapper.modifyAdminBoard(boardDto);
		return check==1;
	}

	@Override
	public boolean deleteAdminBoard(int articleNo,String path) throws Exception {
		int check = adminBoardMapper.deleteAdminBoard(articleNo);
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
		int totalCount = adminBoardMapper.getTotalAdminBoardCount(param);
		
		return totalCount;
	}

}
