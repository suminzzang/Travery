package com.ssafy.travery.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.travery.model.SidoGugunCodeDto;
import com.ssafy.travery.model.mapper.AreaCodeMapper;

@Service
public class AreaCodeServiceImpl implements AreaCodeService {

	@Autowired
	private AreaCodeMapper areaCodeMapper;
	
	public AreaCodeServiceImpl(AreaCodeMapper areaCodeMapper) {
		this.areaCodeMapper = areaCodeMapper;
	}

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return areaCodeMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(int sido) throws Exception {
		return areaCodeMapper.getGugunInSido(sido);
	}

	@Override
	public SidoGugunCodeDto getAreaName(SidoGugunCodeDto sidoGugunCodeDto) throws Exception {
		return areaCodeMapper.getAreaName(sidoGugunCodeDto);
	}

}
