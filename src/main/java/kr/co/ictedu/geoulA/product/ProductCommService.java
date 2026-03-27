package kr.co.ictedu.geoulA.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.geoulA.vo.ProductCommVO;

@Service
public class ProductCommService {
	@Autowired
	private ProductCommDao productCommDao;
	
	public int totalCount(int prodid) {
		return productCommDao.totalCount(prodid);
	}
	public List<ProductCommVO> listComm(Map<String, Object> map) {
		return productCommDao.listComm(map);
	}
	@Transactional
	public void add (ProductCommVO vo) {
		productCommDao.addComm(vo);
	}
	@Transactional
	public void del (ProductCommVO vo) {
		productCommDao.delComm(vo.getProduct_comm_id());
	}
}
