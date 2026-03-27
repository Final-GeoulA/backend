package kr.co.ictedu.geoulA.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.ProductVO;

@Mapper
public interface ProductDao {
	int totalCount(Map<String, Object> map);
	List<ProductVO> plist(Map<String, Object> map);
	void heart (Map<String, Object> map);
	void like (int prodid);
	void unheart (Map<String, Object> map);
	void unlike (int prodid);
	ProductVO detail(int prodid);
	void hit (int prodid);
	void add (ProductVO vo);	//관리자 권한
	void delete (int prodid);		//관리자 권한
}
