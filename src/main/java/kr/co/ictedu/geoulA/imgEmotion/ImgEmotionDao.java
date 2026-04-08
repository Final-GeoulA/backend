package kr.co.ictedu.geoulA.imgEmotion;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.ImgEmotionVO;

@Mapper
public interface ImgEmotionDao {
	int insertEmotion(ImgEmotionVO vo);
}
