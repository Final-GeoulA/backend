package kr.co.ictedu.geoulA.login.aop;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.LoginLogVO;

@Mapper
public interface LoginLogDao {
	
	@Insert("INSERT INTO LOGIN_LOG VALUES(SEQ_LOGIN_LOG.NEXTVAL,#{email},#{reip},#{uagent},#{status},SYSTIMESTAMP)")
	public void addLog(LoginLogVO vo);
}
