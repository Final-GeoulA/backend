package kr.co.ictedu.geoulA.payment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.PaymentVO;

@Service
public class PaymentService {

    @Value("${toss.payments.secret-key}")
    private String secretKey;

    @Autowired
    private PaymentDao paymentDao;

    public boolean confirmPayment(String paymentKey, String orderId, int amount, int userId) throws Exception {
        // Toss 승인 API 호출
        String credentials = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        String body = String.format(
            "{\"paymentKey\":\"%s\",\"orderId\":\"%s\",\"amount\":%d}",
            paymentKey, orderId, amount
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
            .header("Authorization", "Basic " + credentials)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            PaymentVO vo = new PaymentVO();
            vo.setPayment_id(paymentKey);
            vo.setOrder_id(orderId);
            vo.setUser_id(userId);
            vo.setAmount(amount);
            vo.setStatus("DONE");

            paymentDao.insertPayment(vo);
            paymentDao.updateUserGrade(userId);
            return true;
        }
        return false;
    }
}
