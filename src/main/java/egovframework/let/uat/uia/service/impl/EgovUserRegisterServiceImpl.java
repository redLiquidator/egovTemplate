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
@Service("userRegisterService")
public class EgovUserRegisterServiceImpl implements EgovUserRegisterService {

	@Resource(name = "loginDAO")
	private LoginDAO loginDAO;
	
	/**
	 * 아이디 중복여부 확인
	 */
	public Integer countId(LoginVO vo) throws Exception {
		
		return loginDAO.countId(vo);
	}
	
	/**
	 * 회원가입
	 * @return 
	 */
	public void userRegister(LoginVO vo) throws Exception {

		loginDAO.userRegister(vo);
	}


}