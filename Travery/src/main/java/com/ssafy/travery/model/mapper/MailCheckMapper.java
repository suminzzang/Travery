package com.ssafy.travery.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travery.model.MailCheckDto;

@Mapper
public interface MailCheckMapper {

	String getValue(Map<String, String> map) throws SQLException;
	int setKey(MailCheckDto mailCheckDto) throws SQLException;
	int removeKey(String token) throws SQLException;

	String getSalt(String mailKey) throws SQLException;
	void setSalt(Map<String, String> map) throws SQLException;
}
