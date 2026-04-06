package kr.co.ictedu.geoulA.boardSkin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.BoardSkinVO;

@Service
public class BoardSkinService {
	@Autowired
	private BoardSkinDao boardDao;
	
	public void add (BoardSkinVO vo) {
		boardDao.add(vo);
	}
	public void del (int num) {
		boardDao.delete(num);
	}
	public List<BoardSkinVO> blist(Map<String, Object> map) {
		return boardDao.blist(map);
	}
	void hit (int num) {
		boardDao.hit(num);
	}
	public void elike (int num) {
		boardDao.elike(num);
	}
	public void boardUpdate(BoardSkinVO vo) {
		boardDao.boardUpdate(vo);
	}
	public BoardSkinVO detail(int num) {
		hit(num);
		return boardDao.detail(num);
	}
	public int totalCount(Map<String, String> map) {
		return boardDao.totalCount(map);
	};

}
