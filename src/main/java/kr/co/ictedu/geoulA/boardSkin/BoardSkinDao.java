package kr.co.ictedu.geoulA.boardSkin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.BoardSkinVO;

@Mapper
public interface BoardSkinDao {
	void add (BoardSkinVO vo);
	List<BoardSkinVO> blist(Map<String, Object> map);
	
	void hit (int num);
	void elike (int num);
	void boardUpdate(BoardSkinVO vo);
	BoardSkinVO detail(int num);
	void delete (int num);
	int totalCount(Map<String, String> map);
	
}
