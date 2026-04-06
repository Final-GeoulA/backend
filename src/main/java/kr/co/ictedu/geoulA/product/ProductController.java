package kr.co.ictedu.geoulA.product;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.geoulA.passwordless.MessageUtils;
import kr.co.ictedu.geoulA.vo.ProductCommVO;
import kr.co.ictedu.geoulA.vo.UsersVO;
import kr.co.ictedu.geoulA.vo.PageVO;
import kr.co.ictedu.geoulA.vo.ProductVO;


@RestController
@RequestMapping("/board/product")
public class ProductController {

    private final MessageUtils messageUtils;
    @Autowired
    private PageVO pageVO;
    
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCommService productCommService;
	
	@Value("${spring.servlet.multipart.location}")
	private String filePath;

    ProductController(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }
	
	@RequestMapping("/list")
	public Map<String, Object> PList(@RequestParam Map<String, Object> paramMap, HttpServletRequest req){
		pageVO.setNumPerPage(12);
		String cPage = (String) paramMap.get("cPage");

		int totalCnt = productService.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);
		
		int totalPage =(int)Math.ceil(totalCnt/ (double)pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		
		int totalBlock=(int)Math.ceil(totalPage/(double)pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		
		if(cPage !=null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		}else {
			pageVO.setNowPage(1);
		}
		pageVO.setBeginPerPage((pageVO.getNowPage()-1)*pageVO.getNumPerPage()+1);	//(한 페이지에) 이 게시글부터
		pageVO.setEndPerPage(pageVO.getBeginPerPage()+pageVO.getNumPerPage()-1);	//(한 페이지에) 이 게시글까지
		
	
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> map =new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<ProductVO> list = productService.plist(map);

		int startPage =(int)((pageVO.getNowPage()-1)/pageVO.getPagePerBlock())*pageVO.getPagePerBlock()+1;
		int endPage=startPage+pageVO.getPagePerBlock()-1;
		//블록 초기화 전체 페이지값보다 크다면 전체 페이지값을 마지막 블로페이지 값으로 저장
		if(endPage>pageVO.getTotalPage()) {
			endPage=pageVO.getTotalPage();
		}
				
		response.put("data", list);   //페이징 처리가 완료된 리스트를 저장한 데이타	
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;		
	}
	@RequestMapping("/heart")
	public ResponseEntity<?> heart(@RequestParam Map<String, Object> paramMap) {
		productService.heart(paramMap);
		productService.like(Integer.parseInt((String) paramMap.get("prodid")));
	    return ResponseEntity.ok().body("ok");
	}
	@RequestMapping("/unheart")
	public ResponseEntity<?> unheart(@RequestParam Map<String, Object> paramMap) {
		productService.unheart(paramMap);
		productService.unlike(Integer.parseInt((String) paramMap.get("prodid")));
	    return ResponseEntity.ok().body("ok");
	}
	@GetMapping("/detail")
	public ProductVO detail(@RequestParam Map<String, Object> paramMap) {
		productService.hit(Integer.parseInt((String) paramMap.get("prodid")));
		return productService.detail(paramMap);
	}
	@PostMapping("/add")
	public ResponseEntity<?> addBoard(ProductVO vo, HttpServletRequest req) {
		productService.add(vo);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");	
	}
	@GetMapping("/del")
	public void DelBoard(@RequestParam("prodid") int prodid) {
		productService.delete(prodid);
	}


	@PostMapping("/commadd")
	public ResponseEntity<?> boardComm(@RequestBody ProductCommVO vo, HttpServletRequest req, HttpSession session){
		UsersVO loginMember = (UsersVO) session.getAttribute("loginMember");
		if (loginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}
		vo.setUser_id(String.valueOf(loginMember.getUser_id()));
		vo.setUser_name(loginMember.getNickname());
		vo.setReip(req.getRemoteAddr());
		productCommService.add(vo);
		return ResponseEntity.ok().body("ok");
	}
	@RequestMapping("/commlist")
	public Map<String, Object> boardcommList(@RequestParam Map<String, Object> paramMap, HttpServletRequest req){
		pageVO.setNumPerPage(10);
		String cPage = (String) paramMap.get("cPage");

		int totalCnt = productCommService.totalCount(Integer.parseInt((String) paramMap.get("prodid")));
		pageVO.setTotalRecord(totalCnt);
		
		int totalPage =(int)Math.ceil(totalCnt/ (double)pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		
		int totalBlock=(int)Math.ceil(totalPage/(double)pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		
		if(cPage !=null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		}else {
			pageVO.setNowPage(1);
		}
		
		pageVO.setBeginPerPage((pageVO.getNowPage()-1)*pageVO.getNumPerPage()+1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage()+pageVO.getNumPerPage()-1);
		
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> map =new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<ProductCommVO> list = productCommService.listComm(map);
		
		int startPage =(int)((pageVO.getNowPage()-1)/pageVO.getPagePerBlock())*pageVO.getPagePerBlock()+1;
		int endPage=startPage+pageVO.getPagePerBlock()-1;
		//블록 초기화 전체 페이지값보다 크다면 전체 페이지값을 마지막 블로페이지 값으로 저장
		if(endPage>pageVO.getTotalPage()) {
			endPage=pageVO.getTotalPage();
		}
				
		response.put("data", list);   //페이징 처리가 완료된 리스트를 저장한 데이타	
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;
	}
	
	@PostMapping("/delcomm")
    public void delComm(@RequestBody ProductCommVO vo) {
		productCommService.del(vo);       
    }

	@GetMapping("/similar")
	public List<ProductVO> similar(@RequestParam Map<String, Object> paramMap) {
		return productService.similar(paramMap);
	}
}