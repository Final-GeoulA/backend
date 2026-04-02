package kr.co.ictedu.geoulA.skinimg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.ictedu.geoulA.vo.SkinImgVO;

@Service
public class SkinImgService {

    @Autowired
    private SkinImgDao skinImgDao;

    public int insertSkinImg(SkinImgVO vo) {
        return skinImgDao.insertSkinImg(vo);
    }
}
