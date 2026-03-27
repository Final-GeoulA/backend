package kr.co.ictedu.geoulA.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.vo.UsersVO;
	

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LoginDao loginDao;
	
	@PostMapping("/dologin")
	public String doLogin(HttpSession session, HttpServletRequest request, @RequestBody UsersVO vo) {
	    Map<String, Object> result = loginService.loginCheck(vo);

	    if (result != null && result.get("CNT") != null) {
	        int cnt = ((Number) result.get("CNT")).intValue();
	        if (cnt == 1) {
              vo.setUser_id(((Number) result.get("USER_ID")).intValue());
	            vo.setUser_grade_id(((Number) result.get("USER_GRADE_ID")).intValue());
	            vo.setEmail(result.get("EMAIL").toString());
	            vo.setPassword(result.get("PASSWORD").toString());
	            vo.setNickname(result.get("NICKNAME").toString());
	            vo.setAge(result.get("AGE").toString());
	            vo.setSkin_type(result.get("SKIN_TYPE").toString());
	            vo.setGender(result.get("GENDER").toString());
	            session.setAttribute("loginMember", vo);
	            return "success";
	        }
	    }
	    return "fail";
	}
	@GetMapping("/dologout")
	public String doLogout(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent) {
		System.out.println("로그아웃 완료!");
		session.invalidate();
		return "logout";
	}
	@GetMapping("/session")
	public UsersVO session(HttpSession session) {
		UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
		if (loginMember != null) {
			loginMember.setPassword(null);
		}
		return loginMember; // email,password(null)
	}
}
