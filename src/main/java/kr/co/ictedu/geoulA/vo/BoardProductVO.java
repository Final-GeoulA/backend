package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("boardpvo")
public class BoardProductVO {// 댓글개수, 조회수, 좋아요, 이미지, 첨부파일은 제외
	private int num;
	private String title;		// 프론트에 추가 예정
	private String writer;		// 프론트에 추가 예정
	private String content;
	private	int product_id;
	private String reip;
	private String bdate;		// 프론트에 추가 예정
}
