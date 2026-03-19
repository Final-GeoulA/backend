package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("bcommvo")
public class Board_CommVO {
//	private int comm_num;
	private int ucode;
	private String uwriter;
//	private int member_num;
	private String ucontent;
	private int elike;
	private String reip;
	private String bcdate;
	
}
