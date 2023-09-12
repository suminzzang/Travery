package com.ssafy.travery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travery.model.MailCheckDto;
import com.ssafy.travery.model.MemberDto;
import com.ssafy.travery.model.service.JwtServiceImpl;
import com.ssafy.travery.model.service.MailCheckService;
import com.ssafy.travery.model.service.MemberService;
import com.ssafy.travery.util.KeyMaker;
import com.ssafy.travery.util.SaltMaker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/mail")
@Api("메일 인증 컨트롤러 API V1")
public class MailCheckController {

	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MailCheckService mailCheckService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@ApiOperation(value = "인증 값 반환", notes = "Key와 Value가 일치하는 인증값이 있는지 확인한다.", response = Map.class)
	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> getValue(
			@RequestBody @ApiParam(value = "인증시 필요한 정보(인증 번호)", required = true) MailCheckDto mailCheckDto,
			HttpServletRequest request) {

		String key = mailCheckDto.getMailKey();
		String token = request.getHeader("mail-token");
		logger.debug("Get mailKey info : {}", key);
		Map<String, String> mailMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		mailMap.put("token", token);
		mailMap.put("mailKey", key);
		HttpStatus status = null;
		try {
			String salt = mailCheckService.getSalt(token);
			if(jwtService.isUsable(request.getHeader("mail-token"), salt)) {
				String mailKey = mailCheckService.getValue(mailMap);
				if(mailKey != null) {
					resultMap.put("message", SUCCESS);
					mailCheckService.removeKey(token);
				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			} else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
				mailCheckService.removeKey(token);
			}
		} catch (Exception e) {
			logger.error("인증값 반환 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "키 생성 및 메일 전송", notes = "키 생성 후 메일을 전송한다.", response = Map.class)
	@PostMapping("/send")
	public ResponseEntity<Map<String, Object>> setKey(
			@RequestBody @ApiParam(value = "인증키를 전송할 이메일", required = true) MemberDto memberDto) {

		StringBuilder email = new StringBuilder();
		email.append(memberDto.getEmailId()).append("@").append(memberDto.getEmailDomain());
		
		logger.debug("Generate memberDto info : {}", email);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> saltMap = new HashMap<String, String>();
		Map<String, String> emailMap = new HashMap<String, String>();
		emailMap.put("emailId", memberDto.getEmailId());
		emailMap.put("emailDomain", memberDto.getEmailDomain());
		HttpStatus status = null;
		try {
			boolean duplicated = memberService.emailCheck(emailMap) != 0;
			if(!duplicated) {
				String salt = SaltMaker.getSalt();
				String key = KeyMaker.getKey();
				String token = jwtService.createMailToken("key", key, salt);
				MailCheckDto mailCheckDto = new MailCheckDto();
				mailCheckDto.setMailKey(key);
				mailCheckDto.setToken(token);
				saltMap.put("token", token);
				saltMap.put("salt", salt);
				mailCheckService.setKey(mailCheckDto);
				mailCheckService.setSalt(saltMap);
				mailCheckService.sendMail(email.toString(), key);
				
				resultMap.put("mail-token", token);
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "아이디 정보 전송", notes = "아이디 찾기 메일을 전송한다.", response = Map.class)
	@PostMapping("/sendid")
	public ResponseEntity<Map<String, Object>> findId(
			@RequestBody @ApiParam(value = "아이디를 전송할 이메일", required = true) String email) {

		logger.debug("Find Id email info : {}", email);
		int sep = email.indexOf('@');
		String emailId = email.substring(1, sep);
		String emailDomain = email.substring(sep+1, email.length()-1);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> emailMap = new HashMap<String, String>();
		emailMap.put("emailId", emailId);
		emailMap.put("emailDomain", emailDomain);
		HttpStatus status = null;
		try {
			String userId = memberService.findId(emailMap);
			if(userId == null) {
				resultMap.put("message", FAIL);
			} else {
				mailCheckService.sendFindIdMail(email, userId);
				resultMap.put("message", SUCCESS);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "비밀번호 찾기 키 생성 및 메일 전송", notes = "키 생성 후 메일을 전송한다.", response = Map.class)
	@PostMapping("/sendpw")
	public ResponseEntity<Map<String, Object>> setKeyPw(
			@RequestBody @ApiParam(value = "인증키를 전송할 이메일", required = true) MemberDto memberDto) {

		StringBuilder email = new StringBuilder();
		email.append(memberDto.getEmailId()).append("@").append(memberDto.getEmailDomain());
		
		logger.debug("Change Password memberDto info : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> saltMap = new HashMap<String, String>();
		HttpStatus status = null;
		try {
			boolean isExist = memberService.findPwEmailCheck(memberDto) != 0;
			if(isExist) {
				String salt = SaltMaker.getSalt();
				String key = KeyMaker.getKey();
				String token = jwtService.createMailToken("key", key, salt);
				MailCheckDto mailCheckDto = new MailCheckDto();
				mailCheckDto.setMailKey(key);
				mailCheckDto.setToken(token);
				saltMap.put("token", token);
				saltMap.put("salt", salt);
				mailCheckService.setKey(mailCheckDto);
				mailCheckService.setSalt(saltMap);
				mailCheckService.sendFindPwMail(email.toString(), key);
				
				resultMap.put("mail-token", token);
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
