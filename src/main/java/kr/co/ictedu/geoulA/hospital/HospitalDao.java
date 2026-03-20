package kr.co.ictedu.geoulA.hospital;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.ictedu.geoulA.vo.HospitalVO;

@Mapper
public interface HospitalDao {

    // placeId로 기존 병원 조회
    HospitalVO findByPlaceId(@Param("placeId") String placeId);

    // 병원 저장
    void insertHospital(HospitalVO hospital);

    // 북마크 저장
    void insertHospitalSave(@Param("hospitalId") int hospitalId,
                            @Param("userId") int userId);
    
    // 북마크 저장 취소
    void deleteHospitalSave(@Param("hospitalId") int hospitalId,
            @Param("userId") int userId);
    
    List<HospitalVO> findSavedHospitalsByUserId(@Param("userId") int userId);
}



