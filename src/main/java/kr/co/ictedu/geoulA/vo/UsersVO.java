package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("uvo")
public class UsersVO {
	private int user_id; // 유저 id
	private int user_grade_id;// 유저 등급?
	private String email; // 이메일
	private String password; //비밀번호
	private String nickname; // 별명
	private int age; // 나이
	private int skin_type; // 피부타입
	private int user_coin; // 유저코인
	private int gender;
}
