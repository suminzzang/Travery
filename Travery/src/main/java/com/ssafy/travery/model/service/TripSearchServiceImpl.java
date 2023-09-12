package com.ssafy.travery.model.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.ssafy.travery.model.TripParameterDto;
import com.ssafy.travery.model.TripSearchDto;
import com.ssafy.travery.model.mapper.TripSearchMapper;
import com.ssafy.travery.util.PageNavigation;
import com.ssafy.travery.util.SizeConstant;

@Service
public class TripSearchServiceImpl implements TripSearchService {
	
	private TripSearchMapper tripSearchMapper;
	
	public TripSearchServiceImpl(TripSearchMapper tripSearchMapper) {
		this.tripSearchMapper = tripSearchMapper;
	}
	
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = tripSearchMapper.getTotalUserBoardCount(param);
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
	public List<TripSearchDto> listArticle(TripParameterDto tripParameterDto) throws Exception{
		int start = tripParameterDto.getPg() == 0 ? 0 : (tripParameterDto.getPg() - 1) * tripParameterDto.getSpp();
		tripParameterDto.setStart(start);
		List<TripSearchDto> list = tripSearchMapper.listArticle(tripParameterDto);
		for(TripSearchDto board:list) {
			double value = calDistance(board.getLatitude(),board.getLongitude(),tripParameterDto.getLatitude(),tripParameterDto.getLongitude())/1000.0;
			value=Math.round(value*100)/100.0;
			board.setDistance(value);
		}
		if(tripParameterDto.getSortType()==1) {
			return list;
		}else {
			Queue<TripSearchDto> queue = distArticle(list);
			list = new ArrayList<>();
			while(!queue.isEmpty()) {
				list.add(queue.poll());
			}
			return list;
		}

	}
	
	@Override
	public TripSearchDto getArticle(int contentId) throws Exception {
		return tripSearchMapper.getArticle(contentId);
	}
	
	@Override
	public Queue<TripSearchDto> distArticle(List<TripSearchDto> list) throws Exception {
		Queue<TripSearchDto> queue = new PriorityQueue<>( new Comparator<TripSearchDto>() {
			@Override
			public int compare(TripSearchDto o1, TripSearchDto o2) {
				int value = 0;
				if((o1.getDistance()-o2.getDistance())>0) {
					value=1;
				}else {
					value=-1;
				}
				return value;
			}
		});
		
		for(TripSearchDto board:list) {
			queue.offer(board);
		}
		
		return queue;
	}
	
	public double calDistance(double lat1, double lon1, double lat2, double lon2){  
	    
	    double theta, dist;  
	    theta = lon1 - lon2;  
	    dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))   
	          * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));  
	    dist = Math.acos(dist);  
	    dist = rad2deg(dist);  
	      
	    dist = dist * 60 * 1.1515;   
	    dist = dist * 1.609344;    // 단위 mile 에서 km 변환.  
	    dist = dist * 1000.0;      // 단위  km 에서 m 로 변환  
	  
	    return dist;  
	}  
	  
	    // 주어진 도(degree) 값을 라디언으로 변환  
	private double deg2rad(double deg){  
	    return (double)(deg * Math.PI / (double)180d);  
	}  
	  
	    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환  
	private double rad2deg(double rad){  
	    return (double)(rad * (double)180d / Math.PI);  
	}
	
	@Override
	public int getNum(TripParameterDto tripParameterDto) throws Exception {
		int totalCount = tripSearchMapper.getTotalTripSearchCount(tripParameterDto);
		
		return totalCount;
	}

}
