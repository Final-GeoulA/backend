package kr.co.ictedu.geoulA.vo;

import lombok.Data;

@Data
public class HospitalVO {
	
    private int hospitalId;     // 내부 병원 PK
    private String placeId;     // 카카오 장소 ID
    private String name;        // 병원 이름
    private String address;     // 병원 주소
    private double lat;         // 위도
    private double lon;         // 경도

}
