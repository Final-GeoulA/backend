package kr.co.ictedu.geoulA.skinimg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.config.S3Service;
import kr.co.ictedu.geoulA.vo.SkinImgVO;
import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/api/skinImg")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SkinImgController {

    @Autowired
    private SkinImgService skinImgService;

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadSkinImg(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        try {
            String imgUrl = s3Service.upload(file);

            SkinImgVO vo = new SkinImgVO();
            vo.setUserId((long) loginMember.getUser_id());
            vo.setImg(imgUrl);

            int cnt = skinImgService.insertSkinImg(vo);

            result.put("success", cnt > 0);
            result.put("imgUrl", imgUrl);
            result.put("message", cnt > 0 ? "저장 성공" : "저장 실패");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "업로드 실패: " + e.getMessage());
        }

        return result;
    }
//    @GetMapping("/latest")
//    public Map<String, Object> getLatestSkinImg(HttpSession session) {
//
//        Map<String, Object> result = new HashMap<>();
//
//        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
//
//        if (loginMember == null) {
//            result.put("success", false);
//            result.put("message", "로그인이 필요합니다.");
//            return result;
//        }
//
//        SkinImgVO img = skinImgService
//                .selectLatestSkinImg((long) loginMember.getUser_id());
//
//        result.put("success", true);
//        result.put("img", img);
//
//        return result;
//    }
    
    @GetMapping("/list")
    public Map<String, Object> getSkinImgList() {
        Map<String, Object> result = new HashMap<>();

        try {
            List<SkinImgVO> list = skinImgService.getLatestSkinImgs();

            result.put("success", true);
            result.put("list", list);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }

        return result;
    }
}
