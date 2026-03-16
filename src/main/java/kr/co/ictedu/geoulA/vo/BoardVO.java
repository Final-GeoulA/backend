package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("boardvo")
public class BoardVO {
	private int num;
	private String title;
	private String writer;
	private int member_num;
	private String content;
	private String imgn;
	private int hit;
	private int elike;
	private String reip;
	private String bdate;
	private String comm_count;
	private MultipartFile mfile;
	
	
}
