package kr.co.ictedu.geoulA.hospital;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.HospitalVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalDao hospitalDao;

    public void saveHospital(HospitalVO vo, int userId) {

        HospitalVO existing = hospitalDao.findByPlaceId(vo.getPlaceId());

        int hospitalId;

        if (existing == null) {
            hospitalDao.insertHospital(vo);
            hospitalId = vo.getHospitalId();
        } else {
            hospitalId = existing.getHospitalId();
        }

        hospitalDao.insertHospitalSave(hospitalId, userId);
    }

    public void deleteHospital(String placeId, int userId) {
        HospitalVO existing = hospitalDao.findByPlaceId(placeId);

        if (existing != null) {
            hospitalDao.deleteHospitalSave(existing.getHospitalId(), userId);
        }
    }

    public List<HospitalVO> getSavedHospitals(int userId) {
        return hospitalDao.findSavedHospitalsByUserId(userId);
    }
}