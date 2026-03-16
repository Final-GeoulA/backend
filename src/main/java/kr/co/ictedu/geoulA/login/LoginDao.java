package kr.co.ictedu.geoulA.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.UsersVO;

@Mapper
public interface LoginDao {
	// 일반 로그인
	Map<String, Object> loginCheck(UsersVO vo);
	void changePassword(UsersVO vo);
}
