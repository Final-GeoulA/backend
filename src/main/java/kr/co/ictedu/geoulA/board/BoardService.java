package kr.co.ictedu.geoulA.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.BoardVO;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDao;
	
	public void add (BoardVO vo) {
		boardDao.add(vo);
	}
	public void del (int num) {
		boardDao.delete(num);
	}
	public List<BoardVO> blist(Map<String, String> map) {
		return boardDao.blist(map);
	}
	void hit (int num) {
		boardDao.hit(num);
	}
	public void elike (int num) {
		boardDao.elike(num);
	}
	public BoardVO detail(int num) {
		hit(num);
		return boardDao.detail(num);
	}
//	public int totalCount(Map<String, String> map) {
//		return boardDao.totalCount(map);
//	};

}
