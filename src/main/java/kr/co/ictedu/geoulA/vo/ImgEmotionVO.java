package kr.co.ictedu.geoulA.vo;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgEmotionVO {
    private Long imgEmotionId;
    private Long imgId;				// 이게 참조 속성
    private String imgname;
    private String emotion;
    private Timestamp SSTIME;
}
