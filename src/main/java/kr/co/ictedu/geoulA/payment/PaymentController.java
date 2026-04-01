package kr.co.ictedu.geoulA.payment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.vo.UsersVO;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody Map<String, Object> body, HttpSession session) {
        UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String paymentKey = (String) body.get("paymentKey");
        String orderId = (String) body.get("orderId");
        int amount = (int) body.get("amount");
        int userId = loginMember.getUser_id();

        try {
            boolean success = paymentService.confirmPayment(paymentKey, orderId, amount, userId);
            if (success) {
                // 세션의 등급도 업데이트
                loginMember.setUser_grade_id(2);
                session.setAttribute("loginMember", loginMember);
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.status(400).body("결제 승인 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }
}
