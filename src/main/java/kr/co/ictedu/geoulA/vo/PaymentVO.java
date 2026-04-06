package kr.co.ictedu.geoulA.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVO {
    private String payment_id;  // Toss paymentKey
    private String order_id;
    private int user_id;
    private int amount;
    private String status;
}
