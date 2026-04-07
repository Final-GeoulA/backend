package kr.co.ictedu.geoulA.skinanalysis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.SkinAnalysisDiseaseVO;

@Service
public class SkinAnalysisService {

    @Autowired
    private SkinAnalysisDao skinAnalysisDao;

    public int insertDisease(SkinAnalysisDiseaseVO vo) {
        return skinAnalysisDao.insertDisease(vo);
    }

    public List<SkinAnalysisDiseaseVO> getDiseaseListByUserId(Long userId) {
        return skinAnalysisDao.selectDiseaseListByUserId(userId);
    }

    public SkinAnalysisDiseaseVO getDiseaseByUserSkinImgId(Long userSkinImgId) {
        return skinAnalysisDao.selectDiseaseByUserSkinImgId(userSkinImgId);
    }
}
