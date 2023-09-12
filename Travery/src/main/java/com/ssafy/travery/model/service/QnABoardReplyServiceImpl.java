package com.ssafy.travery.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.travery.model.QnABoardReplyDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.mapper.QnABoardReplyMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;


@Service
public class QnABoardReplyServiceImpl implements QnABoardReplyService {
	
	private QnABoardReplyMapper qnaBoardReplyMapper;
	
	public QnABoardReplyServiceImpl(QnABoardReplyMapper qnaBoardReplyMapper) {
		this.qnaBoardReplyMapper = qnaBoardReplyMapper;
	}

	@Override
	public boolean writeQnABoardReply(QnABoardReplyDto boardDto) throws Exception {
		int check = qnaBoardReplyMapper.writeQnABoardReply(boardDto);
		return check ==1;
	}

	@Override
	public List<QnABoardReplyDto> listQnABoardReply(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return qnaBoardReplyMapper.listQnABoardReply(boardParameterDto);
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
		int totalCount = qnaBoardReplyMapper.getTotalQnABoardReplyCount(param);
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
	public QnABoardReplyDto getQnABoardReply(int articleNo) throws Exception {
		return qnaBoardReplyMapper.getQnABoardReply(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		qnaBoardReplyMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyQnABoardReply(QnABoardReplyDto boardDto) throws Exception {
		int check = qnaBoardReplyMapper.modifyQnABoardReply(boardDto);
		return check==1;
		
	}
	
	@Override
	public boolean successQnABoardReply(QnABoardReplyDto boardDto) throws Exception {
		int check = qnaBoardReplyMapper.successQnABoardReply(boardDto);
		return check==1;
	}

	@Override
	public boolean deleteQnABoardReply(int articleNo,String path) throws Exception {
		int check = qnaBoardReplyMapper.deleteQnABoardReply(articleNo);
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
		int totalCount = qnaBoardReplyMapper.getTotalQnABoardReplyCount(param);
		
		return totalCount;
	}


}
