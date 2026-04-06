package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("logvo")
public class LoginLogVO {
	private int login_log_id;
	private int user_id;
	private String email; // user 테이블 primary key
	private String reip,uagent; // 아이피,에이전트
	private String status; // 상태값
    private String sstime,eetime; // 로그인/로그아웃 시간
}
