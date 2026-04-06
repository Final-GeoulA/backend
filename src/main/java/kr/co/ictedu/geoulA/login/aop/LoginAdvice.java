package kr.co.ictedu.geoulA.login.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.login.LoginDao;
import kr.co.ictedu.geoulA.vo.LoginLogVO;
import kr.co.ictedu.geoulA.vo.UsersVO;

@Component
@Aspect
public class LoginAdvice {
	@Autowired
	private LoginLogDao dao;
	
	private void createLog(String methodName, Object[] fd, ProceedingJoinPoint jp, String status) {
		LoginLogVO logvo = new LoginLogVO();
		if (fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest) {
			HttpSession session = (HttpSession) fd[0];
			HttpServletRequest request = (HttpServletRequest) fd[1];
			UsersVO vo = (UsersVO) session.getAttribute("loginMember");
			if (vo != null) {
				logvo.setUser_id(vo.getUser_id());
				logvo.setEmail(vo.getEmail());
				logvo.setStatus(status);
				logvo.setReip(request.getRemoteAddr());

				String userAgent = request.getHeader("User-Agent");
				String parsedAgent = UserAgentUtils.parseAgent(userAgent);
				logvo.setUagent(parsedAgent);

				dao.addLog(logvo);
			}
		}
	}
	
	@Around("execution(* kr.co.ictedu.geoulA.login.LoginController.doLog*(..))")
	public String loginLogger(ProceedingJoinPoint jp) {
//		해당 타겟의 메서드 dolog로 시작하는 메서드의 매개변수들을 배열로 받아온다.
		Object[] fd = jp.getArgs();
		String rpath = null;
		String methodName = jp.getSignature().getName();
		try {
			if (methodName.equals("doLogin")) {
				rpath = (String) jp.proceed();// dologin메서드 호출
				createLog(methodName, fd, jp, "login"); //login 후 세션 추가
				System.out.println("login 111");
			} else if (methodName.equals("doLogout")) {
				createLog(methodName, fd, jp, "logout"); // 세션 삭제전 사용
				rpath = (String) jp.proceed(); // doLogout 메서드 호출 -세션 사라짐
				System.out.println("logout 111");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println("return:" + rpath);
		System.out.println();
		return rpath;
	}
}
