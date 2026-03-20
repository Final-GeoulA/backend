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
	private int ingredient;		// 성분
	private int cost;			// 가격
	private String image;		// 이미지는 경로만
}