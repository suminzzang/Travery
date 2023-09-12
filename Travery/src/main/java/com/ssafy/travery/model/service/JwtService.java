package com.ssafy.travery.model.service;

import java.util.Map;

public interface JwtService {

	<T> String createMailToken(String key, T data, String salt);
	<T> String createAccessToken(String key, T data, String salt);
	<T> String createRefreshToken(String key, T data, String salt);
	<T> String create(String key, T data, String subject, String salt, long expire);
	Map<String, Object> get(String key, String salt);
	String getUserId(String salt);
	boolean isUsable(String jwt, String salt);

}
