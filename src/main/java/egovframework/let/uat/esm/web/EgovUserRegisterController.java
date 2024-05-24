package egovframework.let.uat.esm.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.uat.uia.service.EgovUserRegisterService;
import egovframework.let.uat.uia.service.impl.LoginDAO;
import egovframework.let.utl.sim.service.EgovFileScrty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * 아이디 중복체크 및 회원등록
 */
@Slf4j
@RestController
@Tag(name="EgovUserRegisterController",description = "회원가입")
public class EgovUserRegisterController {
	/** EgovSiteManagerService */
	@Resource(name = "userRegisterService")
	private EgovUserRegisterService userRegisterService;
	
	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;
	
	
	/**
	 * 회원등록 전 아이디 중복 여부 확인 2024.05.22
	 * 
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	@Operation(
			summary = "아이디중복체크",
			description = "아이디중복체크",
			tags = {"EgovUserRegisterController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "아이디중복체크 성공"),
			@ApiResponse(responseCode = "300", description = "아이디중복체크 실패")
	})
	@PostMapping(value = "/idCheck", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_HTML_VALUE})
	public ResultVO checkId(@RequestBody LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception{	
		log.debug("===>>> EgovUserRegisterController.idCheck() 아이디중복확인 id : "+loginVO.getId());
		// 1. 일치하는 아이디가 있는지 조회한다. 
		int count = loginDAO.countId(loginVO);
		// 2. 일치하는 아이디가 있으면 회원가입 불가 안내, 없으면 회원가입 진행 안내
			ResultVO resultVO = new ResultVO();
		  if(count < 1) {
				//resultMap.put("resultMessage","해당 아이디로 회원등록 가능");
			  log.debug("해당 아이디로 회원등록 가능");
				resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
				resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		  }else {
				//resultMap.put("resultMessage","해당 아이디는 이미 존재합니다");
			  log.debug("해당 아이디는 이미 존재합니다");
				resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
				resultVO.setResultMessage(ResponseCode.SAVE_ERROR.getMessage());
		  }
		
		return resultVO;
		
	}
	

	/**
	 * 회원등록
	 */
	@Operation(
			summary = "회원등록",
			description = "새로운 유저를 DB에 등록한다",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovUserRegisterController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님"),
			@ApiResponse(responseCode = "800", description = "저장시 내부 오류")
	})
	@PostMapping(value = "/userRegister")
	public ResultVO userRegister(@RequestBody LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {
		log.debug("===>>> EgovUserRegisterController.userRegister() 회원등록 id : "+loginVO.getId() +"password : "+loginVO.getPassword());
		ResultVO resultVO = new ResultVO();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("LoginVO", loginVO);
		try {
			String enpassword = EgovFileScrty.encryptPassword(loginVO.getPassword(), loginVO.getId());
			loginVO.setPassword(enpassword);
			userRegisterService.userRegister(loginVO);	
			log.debug("===>>> userRegister service 실행완료.");
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
			
		} catch (SQLException e) {
			log.debug("===>>> SQLException 발생 ");
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.SAVE_ERROR.getMessage());
		}
		return resultVO;

	}
}
