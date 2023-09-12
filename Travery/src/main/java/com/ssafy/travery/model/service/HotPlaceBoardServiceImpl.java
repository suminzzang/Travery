package com.ssafy.travery.model.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.HotPlaceBoardDto;
import com.ssafy.travery.model.HotPlacePhotoDto;
import com.ssafy.travery.model.mapper.HotPlaceBoardMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;

@Service
public class HotPlaceBoardServiceImpl implements HotPlaceBoardService{
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private HotPlaceBoardMapper hotPlaceBoardMapper;
	
	public HotPlaceBoardServiceImpl(HotPlaceBoardMapper hotPlaceBoardMapper) {
		this.hotPlaceBoardMapper = hotPlaceBoardMapper;
	}

	@Override
	public boolean writeHotPlaceBoard(HotPlaceBoardDto hotplaceBoard) throws Exception {
	    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
	        sqlSession.insert("writeHotPlaceBoard", hotplaceBoard);
	        int articleNo = hotplaceBoard.getArticleNo();

	        List<HotPlacePhotoDto> detailList = Arrays.asList(hotplaceBoard.getPhotoUrl());
	        for (HotPlacePhotoDto detailDto : detailList) {
	            detailDto.setParentNo(articleNo);
	        }
	        if(detailList.size()>0) {
	        	sqlSession.insert("writeHotPlacePhoto", detailList);
	        }
	        sqlSession.commit();
	    } catch (Exception e) {
	        throw new Exception("Failed to write hotplace board.", e);
	    }
	    
	    return true;
		
	}

	@Override
	public List<HotPlaceBoardDto> listHotPlaceBoard(BoardParameterDto boardParameterDto) throws SQLException {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return hotPlaceBoardMapper.listHotPlaceBoard(boardParameterDto);
	}
	
	@Override
	public List<HotPlaceBoardDto> monthlyHotPlaceBoard(Map<String, Integer> map) throws SQLException {
		return hotPlaceBoardMapper.monthlyHotPlaceBoard(map);
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
		if ("hotPlaceid".equals(key))
			key = "hotPlace_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = hotPlaceBoardMapper.getTotalHotPlaceBoardCount(param);
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
	public HotPlaceBoardDto getHotPlaceBoard(int articleNo) throws SQLException {
		List<HotPlacePhotoDto> detailList  = hotPlaceBoardMapper.listDetail(articleNo);
		HotPlacePhotoDto[] detailArray = detailList.toArray(new HotPlacePhotoDto[detailList.size()]);
		HotPlaceBoardDto board = hotPlaceBoardMapper.getHotPlaceBoard(articleNo);
		board.setPhotoUrl(detailArray);
		return board;
	}

	@Override
	public void updateHit(int articleNo) throws SQLException {
		hotPlaceBoardMapper.updateHit(articleNo);
	}

	@Override
	public boolean modifyHotPlaceBoard(HotPlaceBoardDto boardDto) throws Exception {
	    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
	        sqlSession.update("modifyHotPlaceBoard", boardDto);
	        int articleNo = boardDto.getArticleNo();
	        sqlSession.delete("deleteHotplacePhoto",articleNo);
	        List<HotPlacePhotoDto> detailList = Arrays.asList(boardDto.getPhotoUrl());

	        for (HotPlacePhotoDto detailDto : detailList) {
	            detailDto.setParentNo(articleNo);
	        }

	        if(detailList.size()>0) {
	        	sqlSession.insert("writeHotPlacePhoto", detailList);
	        }
	        
	        if(detailList.size()==0) {
	        	sqlSession.update("deleteFirstImage",articleNo);
	        }
	        
	        sqlSession.commit();
	    } catch (Exception e) {
	        throw new Exception("Failed to write trip plan board.", e);
	    }
	    
	    return true;
	}

	@Override
	public boolean deleteHotPlaceBoard(int articleNo) throws SQLException {
		int check = hotPlaceBoardMapper.deleteHotPlaceBoard(articleNo);
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
		int totalCount = hotPlaceBoardMapper.getTotalHotPlaceBoardCount(param);
		
		return totalCount;
	}

}
