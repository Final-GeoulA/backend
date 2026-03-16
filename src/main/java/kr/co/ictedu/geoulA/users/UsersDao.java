package kr.co.ictedu.geoulA.users;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.UsersVO;

@Mapper
public interface UsersDao {
	
	void insertUser(UsersVO vo); // 회원가입
	
	int countByEmail(String email);// 이메일 중복 체크 count로 반환
	
	String useCheckEmail(String email);

}
