package kr.co.ictedu.geoulA.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.UsersVO;

@Service
public class UsersService {
	@Autowired
	private UsersDao usersDao;
	
	public void create(UsersVO vo) {
		usersDao.insertUser(vo);
	}
	public int checkEmail(String mail) {
		return usersDao.countByEmail(mail);
	}
	public String useCheckEmail(String mail) {
		return usersDao.useCheckEmail(mail);
	}
    public void changePassword(UsersVO vo) {
    	usersDao.changePassword(vo);
    }
}
