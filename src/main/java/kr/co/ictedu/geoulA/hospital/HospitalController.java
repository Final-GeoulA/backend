package kr.co.ictedu.geoulA.hospital;

import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.vo.HospitalVO;
import kr.co.ictedu.geoulA.vo.UsersVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    // 병원 저장
    @PostMapping("/save")
    public Map<String, Object> saveHospital(@RequestBody HospitalVO vo, HttpSession session) {
        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        hospitalService.saveHospital(vo, loginMember.getUser_id());
        return Map.of("success", true, "message", "병원이 저장되었습니다.");
    }

    // 병원 저장 취소
    @DeleteMapping("/delete")
    public Map<String, Object> deleteHospital(@RequestParam("placeId") String placeId,
                                              HttpSession session) {
        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        hospitalService.deleteHospital(placeId, loginMember.getUser_id());
        return Map.of("success", true, "message", "저장이 취소되었습니다.");
    }

    // 병원 저장 목록
    @GetMapping("/bookmarks")
    public Object getBookmarks(HttpSession session) {
        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            return Map.of("success", false, "message", "로그인이 필요합니다.");
        }

        List<HospitalVO> bookmarks = hospitalService.getSavedHospitals(loginMember.getUser_id());
        return bookmarks;
    }
}