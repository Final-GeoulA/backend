package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("bcommvo")
public class BoardSkinCommVO {
	private int board_skin_comm_id;
	private int board_skin_id;
	private String unickname;
	private String ucontent;
	private int elike;
	private String reip;
	private String bcdate;
}
