package kr.co.ictedu.geoulA.medicalrecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import kr.co.ictedu.geoulA.vo.MedicalRecordVO;

@RestController
@RequestMapping("/api/medical-record")
@CrossOrigin(origins = "http://localhost:3000") // 도메인 허용
public class MedicalRecordController {
	
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private ClovaOcrService clovaOcrService;
    
    @PostMapping("/save")
    public Map<String, Object> saveMedicalRecord(@RequestBody MedicalRecordVO vo) {
        Map<String, Object> result = new HashMap<>();

        int cnt = medicalRecordService.insertMedicalRecord(vo);

        result.put("success", cnt > 0);
        result.put("message", cnt > 0 ? "저장 성공" : "저장 실패");

        return result;
    }

    @GetMapping("/list")
    public List<MedicalRecordVO> getMedicalRecordList(@RequestParam("userId") Long userId) {
        return medicalRecordService.selectMedicalRecordList(userId);
    }

    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> ocrTest(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        try {
            System.out.println("=== 컨트롤러 진입 ===");
            System.out.println("파일명: " + file.getOriginalFilename());
            System.out.println("파일크기: " + file.getSize());

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
