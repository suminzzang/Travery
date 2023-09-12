package com.ssafy.travery.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travery.model.SidoGugunCodeDto;
import com.ssafy.travery.model.service.AreaCodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/areacode")
@Api("지역 코드 컨트롤러 API V1")
public class AreaCodeController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private AreaCodeService areaCodeService;

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.debug("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(areaCodeService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "전국의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.debug("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(areaCodeService.getGugunInSido(Integer.parseInt(sido)), HttpStatus.OK);
	}

	@ApiOperation(value = "지역 이름", notes = "지역 이름을 반환한다.", response = List.class)
	@GetMapping("/name")
	public ResponseEntity<SidoGugunCodeDto> getName(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido,
			@RequestParam("gugun") @ApiParam(value = "구군코드.", required = true) String gugun) throws Exception {
		logger.debug("sido gugun name - 호출");
		SidoGugunCodeDto sidoGugunCodeDto = new SidoGugunCodeDto();
		sidoGugunCodeDto.setSidoCode(Integer.parseInt(sido));
		sidoGugunCodeDto.setGugunCode(Integer.parseInt(gugun));
		return new ResponseEntity<SidoGugunCodeDto>(areaCodeService.getAreaName(sidoGugunCodeDto), HttpStatus.OK);
	}
}
