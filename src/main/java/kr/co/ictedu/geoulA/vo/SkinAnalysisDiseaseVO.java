package kr.co.ictedu.geoulA.vo;

import lombok.Data;

@Data
public class SkinAnalysisDiseaseVO {
    private Long skinAnalysisDiseaseId;
    private Long userSkinImgId;
    private Integer diseaseDry;
    private Integer diseaseAtopy;
    private Integer diseasePimple;
    private Integer diseaseInflam;
    private String created;

    // user_skin_img JOIN 필드
    private String img;
    private String bdate;
}
