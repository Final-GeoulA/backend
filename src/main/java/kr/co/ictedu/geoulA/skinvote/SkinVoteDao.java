package kr.co.ictedu.geoulA.skinvote;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.geoulA.vo.SkinVoteVO;

@Mapper
public interface SkinVoteDao {
	// 투표 기록 추가
    void insertVote(@Param("winnerId") int winnerId, @Param("loserId") int loserId);

    // 특정 유저 승리 수
    int countWin(@Param("id") int id);

    // 특정 유저 패배 수
    int countLose(@Param("id") int id);

    // 전체 유저 승/패 데이터 가져오기
    List<SkinVoteVO> getAllRanks();
}