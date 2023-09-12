package com.ssafy.travery.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travery.model.TripParameterDto;
import com.ssafy.travery.model.TripSearchDto;
import com.ssafy.travery.model.service.TripSearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/tripsearch")
@Api("관광지검색 컨트롤러  API V1")
public class TripSearchController {


	private final Logger logger = LoggerFactory.getLogger(UserBoardController.class);

	/* UPLOAD_PATH => properties에서 설정 */
	@Value("${file.path}")
	private String UPLOAD_PATH;
	
	@Autowired
	private TripSearchService tripSearchService;
	
	public TripSearchController(TripSearchService tripSearchService) {
		this.tripSearchService = tripSearchService;
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다. ", response = List.class)
	@GetMapping
	public ResponseEntity<List<TripSearchDto>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) @ModelAttribute TripParameterDto tripParameterDto) throws Exception {
		logger.debug("listArticle - 호출");
		return new ResponseEntity<List<TripSearchDto>>(tripSearchService.listArticle(tripParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 총개수", notes = "게시판의 총 개수를 반환한다..", response = Integer.class)
	@GetMapping("/total")
	public ResponseEntity<Integer> getNum(@ModelAttribute TripParameterDto tripParameterDto) throws Exception {
		logger.debug("getNum 호출");
		return new ResponseEntity<Integer>(tripSearchService.getNum(tripParameterDto), HttpStatus.OK);
	}
}