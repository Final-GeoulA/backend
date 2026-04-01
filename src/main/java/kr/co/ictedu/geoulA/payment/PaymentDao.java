package kr.co.ictedu.geoulA.payment;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.PaymentVO;

@Mapper
public interface PaymentDao {
    void insertPayment(PaymentVO vo);
    void updateUserGrade(int userId);
}
