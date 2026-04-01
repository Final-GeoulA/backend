package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("prdcommvo")
public class ProductCommVO {
	private int product_comm_id;
	private int product_id;
	private String user_id;
	private String user_name;
	private String content;
	private int elike;
	private String reip;
	private String bcdate;
}
