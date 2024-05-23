package egovframework.let.uat.uia.service.impl;

import egovframework.com.cmm.LoginVO;
import egovframework.let.uat.esm.web.EgovUserRegisterController;
import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.uat.uia.service.EgovUserRegisterService;
import egovframework.let.utl.fcc.service.EgovNumberUtil;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.sim.service.EgovFileScrty;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 회원가입
 * @author 이진주
 * @since 2024.05.23
 * @version 1.0
 * @see
 *
 */
@Slf4j
@Service("userRegisterService")
public class EgovUserRegisterServiceImpl implements EgovUserRegisterService {

	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;
	
	/**
	 * 아이디 중복여부 확인
	 */
	public Integer countId(LoginVO vo) throws Exception {
		
		return loginDAO.countId(LoginVO vo);
	}
	
	/**
	 * 회원가입
	 */
	public LoginVO userRegister(LoginVO vo) throws Exception {

		// 1. 이름, 이메일주소가 DB와 일치하는 사용자 ID를 조회한다.
		LoginVO loginVO = loginDAO.searchId(vo);

		// 2. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}

		return loginVO;
	}


}