package com.ssafy.travery.model.service;

import java.util.Map;

import com.ssafy.travery.model.MailCheckDto;

public interface MailCheckService {

	String getValue(Map<String, String> map) throws Exception;
	int setKey(MailCheckDto mailCheckDto) throws Exception;
	int removeKey(String token) throws Exception;

	String getSalt(String mailKey) throws Exception;
	void setSalt(Map<String, String> map) throws Exception;
	
	public void sendMail(String email, String key);
	public void sendFindIdMail(String email, String userId);
	public void sendFindPwMail(String email, String key);
}
