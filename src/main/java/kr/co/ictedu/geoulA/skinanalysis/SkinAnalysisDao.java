package kr.co.ictedu.geoulA.skinanalysis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.SkinAnalysisDiseaseVO;

@Mapper
public interface SkinAnalysisDao {
    int insertDisease(SkinAnalysisDiseaseVO vo);

    List<SkinAnalysisDiseaseVO> selectDiseaseListByUserId(Long userId);

    SkinAnalysisDiseaseVO selectDiseaseByUserSkinImgId(Long userSkinImgId);
}
