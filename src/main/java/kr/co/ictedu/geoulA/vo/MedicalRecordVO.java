package kr.co.ictedu.geoulA.vo;
import lombok.Data;

@Data
public class MedicalRecordVO {
    private Long medicalRecordId;
    private Long userId;
    private String hospitalName;
    private String paymentDate;   
    private Integer price;
    private String memo;
}
