package kr.co.ictedu.geoulA.medicalrecord;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.MedicalRecordVO;

@Mapper
public interface MedicalRecordDao {
    int insertMedicalRecord(MedicalRecordVO vo);
    List<MedicalRecordVO> selectMedicalRecordList(Long userId);
}
