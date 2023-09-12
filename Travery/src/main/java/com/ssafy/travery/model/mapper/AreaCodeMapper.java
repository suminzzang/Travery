package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travery.model.SidoGugunCodeDto;

@Mapper
public interface AreaCodeMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(int sido) throws SQLException;
	SidoGugunCodeDto getAreaName(SidoGugunCodeDto sidoGugunCodeDto) throws SQLException;
	
}
