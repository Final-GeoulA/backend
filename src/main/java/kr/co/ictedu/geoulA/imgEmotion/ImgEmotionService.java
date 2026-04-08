package kr.co.ictedu.geoulA.imgEmotion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.ImgEmotionVO;
import kr.co.ictedu.geoulA.vo.SkinAnalysisDiseaseVO;

@Service
public class ImgEmotionService {

	@Autowired
	private ImgEmotionDao imgEmotionDao;

	public int insertEmotion(ImgEmotionVO vo) {
		return imgEmotionDao.insertEmotion(vo);
	}
}
