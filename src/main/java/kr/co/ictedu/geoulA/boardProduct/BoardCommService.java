package kr.co.ictedu.geoulA.boardProduct;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.geoulA.vo.BoardSkinCommVO;

@Service
public class BoardCommService {
	@Autowired
	private Board_CommDao boardCommDao;
	
	@Transactional
	public void add (BoardSkinCommVO vo) {
		boardCommDao.addComm(vo);
		boardCommDao.plusComm(vo.getBoard_skin_id());
	}
	
	public List<BoardSkinCommVO> listComm(Map<String, String> map) {
		return boardCommDao.listComm(map);
	}
	
	public int totalCount(Map<String, String> map) {
		return boardCommDao.totalCount(map);
	}
	
	@Transactional
	public void del (BoardSkinCommVO vo) {
		boardCommDao.delcomm(vo.getBoard_skin_comm_id());
		boardCommDao.minusComm(vo.getBoard_skin_id());
	}
}
