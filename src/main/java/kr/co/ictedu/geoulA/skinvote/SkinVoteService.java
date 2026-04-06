package kr.co.ictedu.geoulA.skinvote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.SkinVoteVO;

@Service
public class SkinVoteService {
	 @Autowired
	    private SkinVoteDao skinVoteDao;


	    // 투표 추가
	    public void vote(int winnerId, int loserId) {
	        skinVoteDao.insertVote(winnerId, loserId);
	    }

	    // 특정 유저 승/패 가져오기
	    public int getWinCount(int id) {
	        return skinVoteDao.countWin(id);
	    }

	    public int getLoseCount(int id) {
	        return skinVoteDao.countLose(id);
	    }

	    // 전체 랭킹 계산 (승률 포함)
	    public List<SkinVoteVO> getRankList() {
	        List<SkinVoteVO> ranks = skinVoteDao.getAllRanks();

	        for (SkinVoteVO vo : ranks) {
	            int total = vo.getWinCount() + vo.getLoseCount();
	            vo.setWinRate(total > 0 ? (double) vo.getWinCount() / total * 100 : 0);
	        }

	        return ranks;
	    }
	}