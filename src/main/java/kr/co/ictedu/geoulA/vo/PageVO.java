package kr.co.ictedu.geoulA.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PageVO {
	private int nowPage;    //현재 페이지 값
	private int nowBlock;    //현재 블럭
	private int totalRecord;    //총 게시물 수 .Dao로 부터 받음
	private int numPerPage;      //한페이지당 보여질 게시물 수
	private int pagePerBlock;		//한 블럭당 보여질 페이지의 블럭수
	private int totalPage;			//전체 페이지 
	private int totalBlock;			//전체 블럭 수
	private int beginPerPage;		//각 페이지별 시작 게시물의 index값
	private int endPerPage;			//각 페이지별 마지막 게시물의 index값
	
	public PageVO() {		//기본 생성자에서 페이징 처리에 기본 값을 초기화
		this.nowPage=1;
		this.nowBlock=1;
		this.numPerPage=15;
		this.pagePerBlock=5;
	}
}
