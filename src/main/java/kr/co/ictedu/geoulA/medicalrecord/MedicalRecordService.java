package kr.co.ictedu.geoulA.medicalrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.MedicalRecordVO;

@Service
public class MedicalRecordService {

	@Autowired
    private MedicalRecordDao medicalRecordDao;

    public int insertMedicalRecord(MedicalRecordVO vo) {
        return medicalRecordDao.insertMedicalRecord(vo);
    }

    public List<MedicalRecordVO> selectMedicalRecordList(Long userId) {
        return medicalRecordDao.selectMedicalRecordList(userId);
    }	
}
