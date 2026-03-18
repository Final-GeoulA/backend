package kr.co.ictedu.geoulA.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.geoulA.vo.Board_CommVO;

@Service
public class BoardCommService {
	@Autowired
	private Board_CommDao boardCommDao;
	
	@Transactional
	public void add (Board_CommVO vo) {
		boardCommDao.addComm(vo);
//		boardCommDao.plusComm(vo.getBoard_num());
	}
	
	public List<Board_CommVO> listComm(Map<String, String> map) {
		return boardCommDao.listComm(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return boardCommDao.totalCount(map);
	}
	
	@Transactional
	public void del (Board_CommVO num) {
//		boardCommDao.delcomm(num);
//		boardCommDao.minusComm(vo.getBoard_num());
	}
}
