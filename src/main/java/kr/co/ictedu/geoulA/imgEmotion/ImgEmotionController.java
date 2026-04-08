package kr.co.ictedu.geoulA.imgEmotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.vo.ImgEmotionVO;
import kr.co.ictedu.geoulA.vo.SkinAnalysisDiseaseVO;
import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/api/emotionAnalysis")
public class ImgEmotionController {

    @Autowired
    private ImgEmotionService imgEmotionService;

    @PostMapping("/save")
    public Map<String, Object> saveEmotion(@RequestBody ImgEmotionVO vo, HttpSession session){
    	System.out.println("test");
    	Map<String, Object> result = new HashMap<>();
    	
    	UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }
        
        try {
            int cnt = imgEmotionService.insertEmotion(vo);
            result.put("success", cnt > 0);
            result.put("message", cnt > 0 ? "저장 성공" : "저장 실패");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "저장 실패: " + e.getMessage());
        }
        
        return result;
    }
}
