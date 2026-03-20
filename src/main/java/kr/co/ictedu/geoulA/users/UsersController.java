package kr.co.ictedu.geoulA.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/user")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> memberjoin(@RequestBody UsersVO vo) {
		System.out.println(vo.getEmail());
		usersService.create(vo);
		return ResponseEntity.ok().build();
	}
	
}
