package egovframework.let.uat.uia.service;

import egovframework.com.cmm.LoginVO;

/**
 * 아이디중복확인 및 회원가입
 * @author 이진주
 * @since 2024.05.23
 * @version 1.0
 * @see
 
 */
public interface EgovUserRegisterService {
	
	/**
	 * 아이디 중복여부 확인
	 */
	public Integer countId(LoginVO vo) throws Exception;

	/**
	 * 회원가입
	 */
	public LoginVO userRegister(LoginVO vo) throws Exception;

}