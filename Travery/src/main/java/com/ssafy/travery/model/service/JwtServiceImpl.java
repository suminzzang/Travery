package com.ssafy.travery.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.travery.exception.UnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	private static final int ACCESS_TOKEN_EXPIRE_HOURS = 5;
	private static final int MAIL_TOKEN_EXPIRE_MINUTES = 15;
	private static final int REFRESH_TOKEN_EXPIRE_DAYS = 7;

	
	@Override
	public <T> String createMailToken(String key, T data, String salt) {
		return create(key, data, "mail-token", salt, 1000 * 60 * MAIL_TOKEN_EXPIRE_MINUTES);
	}
	
	@Override
	public <T> String createAccessToken(String key, T data, String salt) {
		return create(key, data, "access-token", salt, 1000 * 60 * 60 * ACCESS_TOKEN_EXPIRE_HOURS);
	}

	@Override
	public <T> String createRefreshToken(String key, T data, String salt) {
		return create(key, data, "refresh-token", salt, 1000 * 60 * 60 * 24 * REFRESH_TOKEN_EXPIRE_DAYS);
	}

	@Override
	public <T> String create(String key, T data, String subject, String salt, long expire) {
		Claims claims = Jwts.claims()
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expire));
		claims.put(key, data);
		
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, this.generateKey(salt))
				.compact();

		return jwt;
	}
	
	private byte[] generateKey(String salt) {
		byte[] key = null;
		try {
			key = salt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}
	
	@Override
	public boolean isUsable(String jwt, String salt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey(salt)).parseClaimsJws(jwt);
			logger.debug("claims: {}", claims);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public Map<String, Object> get(String key, String salt) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String jwt = request.getHeader("access-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new UnAuthorizedException();
		}
		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);
		return value;
	}

	@Override
	public String getUserId(String salt) {
		return (String) this.get("user", salt).get("userId");
	}

}
