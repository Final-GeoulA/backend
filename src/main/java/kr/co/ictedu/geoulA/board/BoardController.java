package kr.co.ictedu.geoulA.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.geoulA.vo.BoardVO;
import kr.co.ictedu.geoulA.vo.Board_CommVO;
import kr.co.ictedu.geoulA.vo.PageVO;


@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private PageVO pageVO;
    
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardCommService boardCommService;
	
	@Value("${spring.servlet.multipart.location}")
	private String filePath;
	
	@PostMapping("/add")
	public ResponseEntity<?> addBoard(BoardVO vo, HttpServletRequest req) {
		vo.setReip(req.getRemoteAddr());
		MultipartFile mf = vo.getMfile();
		String oriFn =mf.getOriginalFilename();
		System.out.println("파일이름: "+oriFn);
		
		StringBuilder path = new StringBuilder();
		path.append(filePath).append("\\");
		path.append(oriFn);
		System.out.println("FullPath: "+ path);
		File f = new File(path.toString());		
		try {
			mf.transferTo(f);   			
			vo.setImgn(oriFn);
			boardService.add(vo);
			return ResponseEntity.ok().body("업로드 성공!");	
		}catch (IllegalStateException |IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");	
	}
	@GetMapping("/del")
	public void DelBoard(@RequestParam("num") int num) {
		boardService.del(num);
	}
	
	@GetMapping("/detail")
	public BoardVO detail(@RequestParam("num") int num) {
		return boardService.detail(num);		
	}
	
	
	@RequestMapping("/list")
	public Map<String, Object> BoaradList(@RequestParam Map<String, String> paramMap, HttpServletRequest req){
		pageVO.setNumPerPage(12);
		String cPage = paramMap.get("cPage");

//		int totalCnt = boardService.totalCount(paramMap);
//		pageVO.setTotalRecord(totalCnt);
//		
//		int totalPage =(int)Math.ceil(totalCnt/ (double)pageVO.getNumPerPage());
//		pageVO.setTotalPage(totalPage);
//		
//		int totalBlock=(int)Math.ceil(totalPage/(double)pageVO.getPagePerBlock());
//		pageVO.setTotalBlock(totalBlock);
		
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
		List<BoardVO> list = boardService.blist(map);

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
	
	@PostMapping("/commadd")
	public ResponseEntity<?> boardComm(@RequestBody Board_CommVO vo, HttpServletRequest req){
		vo.setReip(req.getRemoteAddr());		
		boardCommService.add(vo);
		System.out.println(vo.getBoard_num());
		return ResponseEntity.ok().body("ok");
	}
	
//	@GetMapping("/commlist")
//	public List<Board_CommVO> listBoardComm(@RequestParam("num") int num){	
//		return boardCommService.listComm(num);
//	}
	
	@RequestMapping("/commlist")
	public Map<String, Object> boardcommList(@RequestParam Map<String, String> paramMap, HttpServletRequest req){
		
		pageVO.setNumPerPage(7);
		String cPage = paramMap.get("cPage");

		int totalCnt = boardCommService.totalCount(paramMap);
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
		Map<String, String> map =new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<Board_CommVO> list = boardCommService.listComm(map);
		
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
    public void delComm(@RequestBody Board_CommVO vo) {
        boardCommService.del(vo);       
    }
	@PostMapping("/elike")
	public ResponseEntity<?> eLike(@RequestParam("num") int num) {
	    // 서비스 호출하여 DB 업데이트 수행
	    boardService.elike(num);
	    return ResponseEntity.ok().body("ok");
	}
}

