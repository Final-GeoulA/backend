package kr.co.ictedu.geoulA.vo;

import lombok.Data;
import java.util.Date;

public class HospitalSaveVO {

    private int hospitalSaveId; // 북마크 PK
    private int hospitalId;     // 병원 FK
    private int userId;         // 유저 FK
    private Date createdAt;     // 저장일
}
