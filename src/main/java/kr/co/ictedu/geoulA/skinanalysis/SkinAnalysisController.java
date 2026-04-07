package kr.co.ictedu.geoulA.skinanalysis;

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
import kr.co.ictedu.geoulA.vo.SkinAnalysisDiseaseVO;
import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/api/skinAnalysis")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SkinAnalysisController {

    @Autowired
    private SkinAnalysisService skinAnalysisService;

    @PostMapping("/save")
    public Map<String, Object> saveDisease(
            @RequestBody SkinAnalysisDiseaseVO vo,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        try {
            int cnt = skinAnalysisService.insertDisease(vo);
            result.put("success", cnt > 0);
            result.put("message", cnt > 0 ? "저장 성공" : "저장 실패");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "저장 실패: " + e.getMessage());
        }

        return result;
    }

    @GetMapping("/report")
    public Map<String, Object> getReport(HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        try {
            List<SkinAnalysisDiseaseVO> list =
                    skinAnalysisService.getDiseaseListByUserId((long) loginMember.getUser_id());
            result.put("success", true);
            result.put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "조회 실패: " + e.getMessage());
        }

        return result;
    }


    @GetMapping("/report/{userSkinImgId}")
    public Map<String, Object> getReportByImgId(
            @PathVariable Long userSkinImgId,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        try {
            SkinAnalysisDiseaseVO vo =
                    skinAnalysisService.getDiseaseByUserSkinImgId(userSkinImgId);
            result.put("success", vo != null);
            result.put("data", vo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "조회 실패: " + e.getMessage());
        }

        return result;
    }
}
