package com.ssafy.travery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travery.model.MemberDto;
import com.ssafy.travery.model.service.JwtServiceImpl;
import com.ssafy.travery.model.service.MemberService;
import com.ssafy.travery.util.SaltMaker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/users")
@Api("사용자 컨트롤러 API V1")
public class MemberController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtServiceImpl jwtService;

	@ApiOperation(value = "로그인", notes = "Token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {

		logger.debug("Login memberDto info : {}", memberDto);
		Map<String, String> loginMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		loginMap.put("userId", memberDto.getUserId());
		loginMap.put("userPwd", memberDto.getUserPwd());
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.loginMember(loginMap);
			if(loginUser != null) {
				String salt = memberService.getSalt(loginUser.getUserId());
				String accessToken = jwtService.createAccessToken("userId", loginUser.getUserId(), salt);
				String refreshToken = jwtService.createRefreshToken("userId", loginUser.getUserId(), salt);
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 토큰정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 토큰정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원가입", notes = "회원가입 후 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/join")
	public ResponseEntity<Map<String, Object>> join(
			@RequestBody @ApiParam(value = "회원가입 시 입력한 회원 정보", required = true) MemberDto memberDto) {

		logger.debug("Join memberDto info : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> salt = new HashMap<String, String>();
		HttpStatus status = null;
		try {
			int result = memberService.joinMember(memberDto);
			if(result == 1) {
				salt.put("userId", memberDto.getUserId());
				salt.put("salt", SaltMaker.getSalt());
				memberService.setSalt(salt);
				resultMap.put("message", SUCCESS);
			}
			else {
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

	@ApiOperation(value = "아이디 중복검사", notes = "아이디가 존재하는지 확인한다.", response = Map.class)
	@GetMapping("/idcheck/{userId}")
	public ResponseEntity<Map<String, Object>> duplicateCheck(
			@PathVariable("userId") @ApiParam(value ="중복검사할 아이디", required = true) String userId) {
		logger.debug("ID check id: {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			boolean flag = memberService.idCheck(userId) == 0;
			if(flag) {
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
		
	@ApiOperation(value = "멤버 수정", notes = "멤버 정보를 수정한다.", response = Map.class)
	@PutMapping("/modify")
	public ResponseEntity<Map<String, Object>> updateInfo(
			@RequestBody @ApiParam(value ="수정할 멤버 정보", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		
		logger.debug("Modify memberDto info : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String salt = memberService.getSalt(memberDto.getUserId());
			if(jwtService.isUsable(request.getHeader("access-token"), salt)) {
				int result = memberService.updateInfo(memberDto);
				if(result == 1) {
					Map<String, String> map = new HashMap<>();
					map.put("userId", memberDto.getUserId());
					map.put("userPwd", memberDto.getUserPwd());
					memberService.loginMember(map);
					resultMap.put("message", SUCCESS);
				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			}
			else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "비밀번호 확인", notes = "비밀번호를 확인한다.", response = Map.class)
	@PostMapping("/password/verify")
	public ResponseEntity<Map<String, Object>> passwordCheck(
			@RequestBody @ApiParam(value ="비밀번호 확인", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		logger.debug("Verify member user id: {}", memberDto.getUserId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", memberDto.getUserId());
		map.put("userPwd", memberDto.getUserPwd());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String salt = memberService.getSalt(memberDto.getUserId());
			if(jwtService.isUsable(request.getHeader("access-token"), salt)) {
				boolean flag = memberService.verifyMember(map);
				if(flag) {
					resultMap.put("message", SUCCESS);
				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			} else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "비밀번호 변경", notes = "비밀번호 찾기로 비밀번호를 변경한다.", response = Map.class)
	@PutMapping("/changepassword")
	public ResponseEntity<Map<String, Object>> changePassword(
			@RequestBody @ApiParam(value ="수정할 멤버 정보", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		
		logger.debug("Change Password memberDto info : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			int result = memberService.changePassword(memberDto);
			if(result == 1) {
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "멤버 삭제", notes = "멤버를 삭제한다.", response = Map.class)
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> removeMember(
			@RequestBody @ApiParam(value ="삭제할 멤버 정보", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		logger.debug("Delete member user id: {}", memberDto.getUserId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", memberDto.getUserId());
		map.put("userPwd", memberDto.getUserPwd());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String salt = memberService.getSalt(memberDto.getUserId());
			if(jwtService.isUsable(request.getHeader("access-token"), salt)) {
				boolean flag = memberService.removeMember(map) != 0;
				if(flag) {
					resultMap.put("message", SUCCESS);
				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			} else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "내 정보 조회", notes = "마이페이지에 담길 정보를 조회한다.", response = Map.class)
	@GetMapping("/mypage/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "조회할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
		
		logger.debug("GetInfo user id: {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String salt = memberService.getSalt(userId);
			if(jwtService.isUsable(request.getHeader("access-token"), salt)) {
				MemberDto userInfo = memberService.getInfo(userId);
				if(userInfo != null) {
					resultMap.put("userInfo", userInfo);
					resultMap.put("message", SUCCESS);
				} else {
					resultMap.put("message", FAIL);
				}
				status = HttpStatus.ACCEPTED;
			}
			else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "로그아웃", notes = "로그아웃 하고 Token을 삭제한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<Map<String, Object>> logout(
			@PathVariable("userId") @ApiParam(value = "로그아웃할 아이디.", required = true) String userId,
			HttpServletRequest request) {
		logger.debug("Logout user id: {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			int result = memberService.deleteRefreshToken(userId);
			if(result == 1) {
				resultMap.put("message", SUCCESS);
			} else {
				resultMap.put("message", FAIL);
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "토큰 갱신(Refresh)", notes = "Refresh Token을 갱신한다.", response = Map.class)
	@PostMapping("/refresh/{userId}")
	public ResponseEntity<Map<String, Object>> refresh(
			@PathVariable("userId") @ApiParam(value = "토큰 갱신할 아이디.", required = true) String userId,
			HttpServletRequest request) {

		logger.debug("Refresh token user id : {}", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String salt = memberService.getSalt(userId);
			if(jwtService.isUsable(request.getHeader("access-token"), salt)) {
				String accessToken = jwtService.createAccessToken("userId", userId, salt);
				String refreshToken = jwtService.createRefreshToken("userId", userId, salt);
				memberService.saveRefreshToken(userId, refreshToken);
				logger.debug("토큰 갱신 accessToken 토큰정보 : {}", accessToken);
				logger.debug("토큰 갱신 refreshToken 토큰정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
			else {
				logger.error("사용 불가능 토큰!!!");
				resultMap.put("message", FAIL);
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
			
}