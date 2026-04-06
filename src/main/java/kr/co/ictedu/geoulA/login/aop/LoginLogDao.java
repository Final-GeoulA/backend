package kr.co.ictedu.geoulA.login.aop;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.geoulA.vo.LoginLogVO;

@Mapper
public interface LoginLogDao {
	
	@Insert("""
		    INSERT INTO LOGIN_LOG 
			    (LOGIN_LOG_ID, USER_ID, EMAIL, REIP, UAGENT, STATUS, SSTIME)
		    VALUES
			    (SEQ_LOGIN_LOG.NEXTVAL, #{user_id}, #{email}, #{reip},
		        #{uagent}, #{status}, SYSTIMESTAMP)
			""")
		public void addLog(LoginLogVO vo);
	
	@Select("""
		SELECT
			LOGIN_LOG_ID, USER_ID, EMAIL, REIP, UAGENT, STATUS,
			TO_CHAR(SSTIME, 'YYYY-MM-DD HH24:MI:SS') AS SSTIME
		FROM LOGIN_LOG
		WHERE USER_ID = #{userId} ORDER BY SSTIME DESC
	""")
	public List<LoginLogVO> getLogsByUserId(int userId);
}

