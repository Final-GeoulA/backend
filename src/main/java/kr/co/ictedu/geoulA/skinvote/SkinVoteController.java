package kr.co.ictedu.geoulA.skinvote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.geoulA.vo.SkinVoteVO;

@RestController
@RequestMapping("/api/vote")
public class SkinVoteController {
	@Autowired 
    private SkinVoteService skinVoteService;

	// 투표 API
	@PostMapping("/do")
	public String doVote(
	    @RequestParam("winnerId") int winnerId,
	    @RequestParam("loserId") int loserId
	) {
	    skinVoteService.vote(winnerId, loserId);
	    return "success";
	}

    // 전체 랭킹 API
    @GetMapping("/rank")
    public List<SkinVoteVO> getRank() {
        return skinVoteService.getRankList();
    }
}