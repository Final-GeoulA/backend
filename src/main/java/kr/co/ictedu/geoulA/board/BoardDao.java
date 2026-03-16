package kr.co.ictedu.geoulA.board;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import kr.co.ictedu.geoulA.vo.BoardVO;
import kr.co.ictedu.geoulA.vo.Board_CommVO;

@Mapper
public interface BoardDao {
	void add (BoardVO vo);
	List<BoardVO> blist(Map<String, Object> map);
	
	void hit (int num);
	void elike (int num);
	BoardVO detail(int num);
	void delete (int num);
	int totalCount(Map<String, String> map);
}
