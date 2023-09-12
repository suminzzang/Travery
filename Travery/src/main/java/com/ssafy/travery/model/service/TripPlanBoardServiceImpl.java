package com.ssafy.travery.model.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.TripDetailDto;
import com.ssafy.travery.model.TripPlanBoardDto;
import com.ssafy.travery.model.mapper.TripPlanBoardMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;

@Service
public class TripPlanBoardServiceImpl implements TripPlanBoardService {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private TripPlanBoardMapper tripPlanBoardMapper;
	
	public TripPlanBoardServiceImpl(TripPlanBoardMapper tripPlanBoardMapper) {
		this.tripPlanBoardMapper = tripPlanBoardMapper;
	}

	@Override
	public boolean writeTripPlanBoard(TripPlanBoardDto boardDto) throws Exception {
	    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
	        sqlSession.insert("writeTripPlanBoard", boardDto);
	        int articleNo = boardDto.getArticleNo();
	        List<TripDetailDto> detailList = Arrays.asList(boardDto.getDetailPlan());
	        for (TripDetailDto detailDto : detailList) {
	            detailDto.setParentNo(articleNo);
	        }
	        sqlSession.insert("writeTripDetail", detailList);

	        sqlSession.commit();
	    } catch (Exception e) {
	        throw new Exception("Failed to write trip plan board.", e);
	    }
	    
	    return true;
	}

	@Override
	public List<TripPlanBoardDto> listTripPlanBoard(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return tripPlanBoardMapper.listTripPlanBoard(boardParameterDto);
	}
	
	@Override
	public List<TripPlanBoardDto> monthlyTripPlanBoard(Map<String, Integer> map) throws Exception {
		return tripPlanBoardMapper.monthlyTripPlanBoard(map);
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
		if ("tripPlanid".equals(key))
			key = "tripPlan_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = tripPlanBoardMapper.getTotalTripPlanBoardCount(param);
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
	public TripPlanBoardDto getTripPlanBoard(int articleNo) throws Exception {
		List<TripDetailDto> detailList  = tripPlanBoardMapper.listDetail(articleNo);
		TripDetailDto[] detailArray = detailList.toArray(new TripDetailDto[detailList.size()]);
		TripPlanBoardDto board = tripPlanBoardMapper.getTripPlanBoard(articleNo);
		board.setDetailPlan(detailArray);
		return board;
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		tripPlanBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyTripPlanBoard(TripPlanBoardDto boardDto) throws Exception {
	    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
	        sqlSession.update("modifyTripPlanBoard", boardDto);

	        List<TripDetailDto> detailList =  Arrays.asList(boardDto.getDetailPlan());

	        for (TripDetailDto detail : detailList) {
	            sqlSession.update("modifyTripDetail", detail);
	        }

	        sqlSession.commit();
	    } catch (Exception e) {
	        throw new Exception("Failed to write trip plan board.", e);
	    }
	    
	    return true;
	}

	@Override
	public boolean deleteTripPlanBoard(int articleNo,String path) throws Exception {
		int check = tripPlanBoardMapper.deleteTripPlanBoard(articleNo);
		return check == 1;
	}
	
	@Override
	public int getNum(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("tripPlanid".equals(key))
			key = "tripPlan_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = tripPlanBoardMapper.getTotalTripPlanBoardCount(param);
		
		return totalCount;
	}
}
