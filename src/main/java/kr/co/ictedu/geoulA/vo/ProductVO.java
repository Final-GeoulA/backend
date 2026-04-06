package kr.co.ictedu.geoulA.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("prdvo")
public class ProductVO {
	private	int product_id;
	private String name;
	private String brand;
	private String category;	// 제품 카테고리
	private String ingredient;		// 성분
	private String cost;			// 가격
	private int hit;
	private int elike;
	private String image;		// 이미지는 외부경로
	private int hearted;		// 현재 유저의 좋아요 여부 (1: 좋아요, 0: 아님)
}