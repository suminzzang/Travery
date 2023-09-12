package com.ssafy.travery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travery.model.AdminBoardDto;
import com.ssafy.travery.model.BoardParameterDto;
import com.ssafy.travery.model.FileInfoDto;
import com.ssafy.travery.model.service.AdminBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/adminboard")
@Api("공지사항 컨트롤러  API V1")
public class AdminBoardController {
	
	private final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	/* UPLOAD_PATH => properties에서 설정 */
	@Value("${file.path}")
	private String UPLOAD_PATH;
	
	@Autowired
	private AdminBoardService adminBoardService;
	
	public AdminBoardController(AdminBoardService adminBoardService) {
		this.adminBoardService = adminBoardService;
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다. ", response = List.class)
	@GetMapping
	public ResponseEntity<List<AdminBoardDto>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) @ModelAttribute BoardParameterDto boardParameterDto) throws Exception {
		logger.debug("listArticle - 호출");
		
		return new ResponseEntity<List<AdminBoardDto>>(adminBoardService.listAdminBoard(boardParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = AdminBoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<AdminBoardDto> getArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception {
		logger.debug("getArticle - 호출 : " + articleno);
		adminBoardService.updateHit(articleno);
		return new ResponseEntity<AdminBoardDto>(adminBoardService.getAdminBoard(articleno), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 총개수", notes = "게시판의 총 개수를 반환한다..", response = Integer.class)
	@GetMapping("/total")
	public ResponseEntity<Integer> getNum(@RequestParam Map<String,String> map) throws Exception {
		logger.debug("getNum 호출");
		return new ResponseEntity<Integer>(adminBoardService.getNum(map), HttpStatus.OK);
	}
	

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody @ApiParam(value = "게시글 정보.", required = true) AdminBoardDto adminBoardDto) throws Exception {
	
	    logger.debug("write adminBoardDto : {}", adminBoardDto);
	
	    List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
	    adminBoardDto.setFileInfos(fileInfos);

	    if(adminBoardService.writeAdminBoard(adminBoardDto)) {
	        return new ResponseEntity<String>("success", HttpStatus.OK);
	    }
	    return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}


	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) AdminBoardDto boardDto) throws Exception {
		logger.debug("modifyArticle - 호출 {}", boardDto);
		
		if (adminBoardService.modifyAdminBoard(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.debug("deleteArticle - 호출");
		if (adminBoardService.deleteAdminBoard(articleno,"temp")) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
