package kr.co.ictedu.geoulA.passwordless;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.ictedu.geoulA.passwordless.LoginPwlDao;
//import kr.co.ictb.ictb.dao.LoginLogDAO;
import kr.co.ictedu.geoulA.vo.UsersVO;
//import kr.co.ictb.ictb.vo.MyLoginLoggerVO;

@Service
public class LoginPwlService implements HandlerInterceptor {
	@Autowired
	private LoginPwlDao loginPwlDao;
	
	public Map<String, Object> loginCheck(UsersVO vo){
		return loginPwlDao.loginCheck(vo);
	}

    // Passwordless용 ID 조회
    public UsersVO checkValidation(UsersVO vo) {
        return loginPwlDao.checkValidation(vo);
    }

    // 비밀번호 변경
    public void updatePassword(UsersVO vo) {
    	loginPwlDao.updatePassword(vo);
    }

    // 회원 정보 조회
    public UsersVO getUserInfo(UsersVO vo) {
        return loginPwlDao.getUserInfo(vo);
    }
}
