package kr.co.ictedu.geoulA.skinimg;

import org.apache.ibatis.annotations.Mapper;
import kr.co.ictedu.geoulA.vo.SkinImgVO;

@Mapper
public interface SkinImgDao {
    int insertSkinImg(SkinImgVO vo);
}
