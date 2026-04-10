package kr.co.ictedu.geoulA.medicalrecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.vo.MedicalRecordVO;
import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/api/medical-record")
public class MedicalRecordController {
	
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private ClovaOcrService clovaOcrService;
    
    @PostMapping("/save")
    public Map<String, Object> saveMedicalRecord(@RequestBody MedicalRecordVO vo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        if (loginMember.getUser_grade_id() != 2) {
            result.put("success", false);
            result.put("message", "프리미엄 회원만 사용 가능한 기능입니다.");
            return result;
        }

        vo.setUserId(Long.valueOf(loginMember.getUser_id()));

        int cnt = medicalRecordService.insertMedicalRecord(vo);

        result.put("success", cnt > 0);
        result.put("message", cnt > 0 ? "저장 성공" : "저장 실패");

        return result;
    }

    @GetMapping("/list")
    public Object getMedicalRecordList(HttpSession session) {
        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        if (loginMember.getUser_grade_id() != 2) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "프리미엄 회원만 사용 가능한 기능입니다.");
            return result;
        }

        return medicalRecordService.selectMedicalRecordList((long) loginMember.getUser_id());
    }

    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> ocrTest(@org.springframework.web.bind.annotation.RequestParam("file") MultipartFile file, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");

        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        if (loginMember.getUser_grade_id() != 2) {
            result.put("success", false);
            result.put("message", "프리미엄 회원만 사용 가능한 기능입니다.");
            return result;
        }

        try {
            String ocrJson = clovaOcrService.callOcr(file);
            OcrParsedResult parsed = clovaOcrService.parseOcrResult(ocrJson);

            result.put("success", true);
            result.put("ocrJson", ocrJson);
            result.put("parsed", parsed);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "OCR 실패: " + e.getMessage());
        }

        return result;
    }
}


