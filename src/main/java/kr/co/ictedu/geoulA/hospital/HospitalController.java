package kr.co.ictedu.geoulA.hospital;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.geoulA.vo.HospitalVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
@CrossOrigin
public class HospitalController {

    private final HospitalService hospitalService;

    // 병원 저장
    @PostMapping("/save")
    public void saveHospital(@RequestBody HospitalVO vo,
    						 @RequestParam("userId") int userId) {
        hospitalService.saveHospital(vo, userId);
    }
    
    // 병원 저장 취소
    @DeleteMapping("/delete")
    public void deleteHospital(@RequestParam("placeId") String placeId,
                               @RequestParam("userId") int userId) {
        hospitalService.deleteHospital(placeId, userId);
    }
    
    // 병원 저장 상태
    @GetMapping("/bookmarks")
    public List<HospitalVO> getBookmarks(@RequestParam("userId") int userId) {
        return hospitalService.getSavedHospitals(userId);
    }
}


