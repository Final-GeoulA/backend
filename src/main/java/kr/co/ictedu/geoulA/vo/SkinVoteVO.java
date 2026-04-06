package kr.co.ictedu.geoulA.vo;

import java.sql.Date;

import lombok.Data;
@Data
public class SkinVoteVO {
	private int userId;      // 유저 ID
    private int winCount;    // 승리 횟수
    private int loseCount;   // 패배 횟수
    private double winRate; 
}
