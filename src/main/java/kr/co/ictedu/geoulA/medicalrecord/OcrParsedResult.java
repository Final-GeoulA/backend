package kr.co.ictedu.geoulA.medicalrecord;

import lombok.Data;

@Data
public class OcrParsedResult {
    private String hospitalName;
    private String paymentDate;
    private Integer price;
    private String rawText;
}
