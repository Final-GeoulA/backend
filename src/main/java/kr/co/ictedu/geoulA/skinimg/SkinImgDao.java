package kr.co.ictedu.geoulA.skinimg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.ictedu.geoulA.vo.SkinImgVO;

@Mapper
public interface SkinImgDao {
    int insertSkinImg(SkinImgVO vo);
    
//    SkinImgVO selectLatestSkinImg(Long userId);

    List<SkinImgVO> selectLatestSkinImgs();
}
