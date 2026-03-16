package kr.co.ictedu.geoulA.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.geoulA.vo.Board_CommVO;

@Mapper
public interface Board_CommDao {

	void addComm(Board_CommVO comm);

	void delcomm(int num);

	List<Board_CommVO> listComm(Map<String, String> map);

	int totalCount(Map<String, String> map);

	void plusComm(@Param("num") int num);

	void minusComm(@Param("num") int num);
}
