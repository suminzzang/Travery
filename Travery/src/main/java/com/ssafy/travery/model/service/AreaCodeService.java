package com.ssafy.travery.model.service;

import java.util.List;

import com.ssafy.travery.model.SidoGugunCodeDto;

public interface AreaCodeService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(int sido) throws Exception;
	SidoGugunCodeDto getAreaName(SidoGugunCodeDto sidoGugunCodeDto) throws Exception;
	
}
