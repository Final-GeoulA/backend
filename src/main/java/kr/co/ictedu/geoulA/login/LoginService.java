package kr.co.ictedu.geoulA.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.UsersVO;


@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	
	public Map<String, Object> loginCheck(UsersVO vo){
		return loginDao.loginCheck(vo);
	}

}
