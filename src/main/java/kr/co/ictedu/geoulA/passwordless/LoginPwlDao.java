package kr.co.ictedu.geoulA.passwordless;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.geoulA.vo.UsersVO;

@Mapper
public interface LoginPwlDao {
	
	@Select("SELECT userid, email, nickname, 1 CNT FROM users WHERE email=#{email} AND PASSWORD = #{password}")
	Map<String, Object> loginCheck(UsersVO vo);
	
	// ----- PASSWORDLESS -----
	
	// 이용자 정보 확인
	UsersVO getUserInfo(UsersVO vo);   
	
    // 이용자 검증
    UsersVO checkValidation(UsersVO vo);
    
    // 비밀번호 변경
    void updatePassword(UsersVO vo);
    
    // 이용자 등록
    void createUserInfo(UsersVO vo);
    
    // 이용자 삭제
    void withdrawUserInfo(UsersVO vo);

    // 비밀번호 변경
    void changePassword(UsersVO vo);
}