package kr.co.ictedu.geoulA.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.geoulA.vo.EmailCheckVO;
import kr.co.ictedu.geoulA.vo.EmailCountCheckVO;


@RestController
@RequestMapping("/api/auth")
public class MailCertificationController {
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private CertificationNumberRedisDao certificationNumberRedisDao;
	
	//최종적으로 해당 이메일이 중복이 아니면 0으로 반환 중복이면 1로 반환 
	@PostMapping("/emailCheck")
	public int sendEmail(@RequestBody EmailCheckVO email) {
		
		int checkEmail = emailSenderService.duplicateEmail(email.getEmail());
		if(checkEmail == 0) {
			emailSenderService.sendEmail(email.getEmail());
			System.out.println("이메일이 요청 되었습니다.:"+email.getEmail());
			return 0;
		}else {
			System.out.println("사용중인 이메일 입니다.:"+email.getEmail());
			return 1;
		}
	}
	
	@PostMapping("/emailCheckPw")
	public int sendEmailPw(@RequestBody EmailCheckVO email) {
		int checkEmail = emailSenderService.duplicateEmail(email.getEmail());
		if(checkEmail == 1) {
			emailSenderService.sendEmailPw(email.getEmail());
			System.out.println("이메일이 요청 되었습니다.:"+email.getEmail());
			return 0;
		}else {
			System.out.println("존재하지 않는 이메일 입니다.:"+email.getEmail());
			return 1;
		}
	}
	
	//최종적으로 해당 이메일에 대한 인증코드가 생성됐으면 그 이메일에 대한 시도횟수,성공,실패를 위한 설정 
	@PostMapping("/emailCheck/certification")
	public ResponseEntity<EmailCountCheckVO> verifyCertificationNumber(@RequestBody EmailCheckVO dto){
		boolean haskey = certificationNumberRedisDao.hasKey(dto.getEmail());
		int attempts = certificationNumberRedisDao.getAttempt(dto.getEmail());
		System.out.println(dto.getEmail());
		System.out.println(dto.getCode());
		if(!haskey) { 
			return ResponseEntity.ok(new EmailCountCheckVO(false,"expired"));
			
		}else if (attempts >= 3) {
			return ResponseEntity.ok(new EmailCountCheckVO(false,"exceeded"));
		}else if (certificationNumberRedisDao.getCertifiRedisNumber(dto.getEmail()).equals(dto.getCode())) {
			certificationNumberRedisDao.deleteCertifiRedisNumber(dto.getEmail());
			return ResponseEntity.ok(new EmailCountCheckVO(true,"of"));
		}else {
			certificationNumberRedisDao.increaseAttempt(dto.getEmail());
			return ResponseEntity.ok(new EmailCountCheckVO(false,"wrong"));
		}
	}
	
	@PostMapping("/temppw")
	public String temporaryPassword() {
		String temp = emailSenderService.createTempPw();
		return temp;
	}
}









