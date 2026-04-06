package kr.co.ictedu.geoulA.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.geoulA.vo.ProductCommVO;

@Mapper
public interface ProductCommDao {
	int totalCount(int prodid);
	List<ProductCommVO> listComm(Map<String, Object> map);
	void addComm(ProductCommVO prdcommvo);
	void delComm(int product_comm_id);
}
