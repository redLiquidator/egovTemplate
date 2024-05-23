package egovframework.let.uat.esm.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.let.uat.esm.service.EgovSiteManagerService;
import egovframework.let.uat.uia.service.EgovUserRegisterService;
import egovframework.let.uat.uia.service.impl.LoginDAO;
import egovframework.let.utl.sim.service.EgovFileScrty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
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
	public HashMap<String, Object> checkId(@RequestBody LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception{	
		log.debug("===>>> EgovUserRegisterController.idCheck() 아이디중복확인 id : "+loginVO.getId());
		// 1. 일치하는 아이디가 있는지 조회한다. 
		 int count = userRegisterService.countId(loginVO);
		// 2. 일치하는 아이디가 있으면 회원가입 불가 안내, 없으면 회원가입 진행 안내
		 HashMap<String, Object> resultMap = new HashMap<String, Object>();
		  if(count < 1) {
				resultMap.put("resultMessage","해당 아이디로 회원등록 가능");
		  }else {
				resultMap.put("resultMessage","해당 아이디는 이미 존재합니다");
		  }
		
		return resultMap;
		
	}
	

	/**
	 * 사이트관리자의 기존 비번과 비교하여 변경된 비밀번호를 저장한다.
	 * @param map데이터: String old_password, new_password
	 * @param request - 토큰값으로 인증된 사용자를 확인하기 위한 HttpServletRequest
	 * @return result - 수정결과
	 * @exception Exception
	 */
	@Operation(
			summary = "회원등록",
			description = "사이트관리자의 기존 비번과 비교하여 변경된 비밀번호를 저장",
			security = {@SecurityRequirement(name = "Authorization")},
			tags = {"EgovSiteManagerApiController"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님"),
			@ApiResponse(responseCode = "800", description = "저장시 내부 오류")
	})
	@PostMapping(value = "/1111")
	public ResultVO updateAdminPassword(
			@Parameter(
					schema = @Schema(type = "object",
					additionalProperties = Schema.AdditionalPropertiesValue.TRUE, 
					ref = "#/components/schemas/passwordMap"),
					style = ParameterStyle.FORM,
					explode = Explode.TRUE
					) @RequestParam Map<String, String> param, HttpServletRequest request, 
			@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception {
		ResultVO resultVO = new ResultVO();

		String old_password = param.get("old_password");
		String new_password = param.get("new_password");
		String login_id = user.getId();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("old_password", EgovFileScrty.encryptPassword(old_password, login_id));
		resultMap.put("new_password", EgovFileScrty.encryptPassword(new_password, login_id));
		resultMap.put("login_id", login_id);
		log.debug("===>>> loginVO.getId() = "+login_id);
//		Integer result = siteManagerService.updateAdminPassword(resultMap); //저장성공 시 1, 실패 시 0 반환
//		log.debug("===>>> result = "+result);
//		if(result > 0) {
//			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
//			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
//		}else{
//			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
//			resultVO.setResultMessage(ResponseCode.SAVE_ERROR.getMessage());
//		}

		return resultVO;
	}
}
