package kr.co.ictedu.geoulA.boardProduct;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.geoulA.vo.BoardSkinCommVO;

@Mapper
public interface BoardProductCommDao {

	void addComm(BoardSkinCommVO comm);

	void delcomm(int board_skin_comm_id);

	List<BoardSkinCommVO> listComm(Map<String, String> map);

	int totalCount(Map<String, String> map);

	void plusComm(@Param("num") int num);

	void minusComm(@Param("num") int num);
}
