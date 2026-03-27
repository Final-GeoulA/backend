package kr.co.ictedu.geoulA.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.geoulA.vo.ProductVO;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	public int totalCount(Map<String, Object> map) {
		return productDao.totalCount(map);
	}
	public List<ProductVO> plist(Map<String, Object> map) {
		return productDao.plist(map);
	}
	public void heart (Map<String, Object> map) {
		productDao.heart(map);
	}
	public void like (int prodid) {
		productDao.like(prodid);
	}
	public void unheart (Map<String, Object> map) {
		productDao.unheart(map);
	}
	public void unlike (int prodid) {
		productDao.unlike(prodid);
	}
	public ProductVO detail(int prodid) {
		return productDao.detail(prodid);
	}
	public void hit (int prodid) {
		productDao.hit(prodid);
	}
	public void add (ProductVO vo) {
		productDao.add(vo);
	}
	public void delete (int prodid) {
		productDao.delete(prodid);
	}
}
