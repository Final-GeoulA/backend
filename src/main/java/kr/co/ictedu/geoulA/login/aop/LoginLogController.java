package kr.co.ictedu.geoulA.login.aop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.geoulA.vo.LoginLogVO;

@RestController
@RequestMapping("/loginlog")
public class LoginLogController {

    @Autowired
    private LoginLogDao loginLogDao;

    @GetMapping("/list")
    public List<LoginLogVO> getLogs(@RequestParam("userId") int userId) {
        return loginLogDao.getLogsByUserId(userId);
    }
}