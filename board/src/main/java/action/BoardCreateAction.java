package action;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dto.BoardDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;


@AllArgsConstructor
public class BoardCreateAction implements Action {
	
	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 1. 가져오기
		BoardDTO insertDto = new BoardDTO();
		
		//insertDto.setBno(Integer.parseInt(request.getParameter("bno")));
		insertDto.setName(request.getParameter("name"));
		insertDto.setPassword(request.getParameter("password"));
		insertDto.setTitle(request.getParameter("title"));
		insertDto.setContent(request.getParameter("content"));
		
		// 페이지 나누기
		int page = Integer.parseInt(request.getParameter("page"));
		int amount = Integer.parseInt(request.getParameter("amount"));

		// 검색기능 추가
		String criteria = request.getParameter("criteria");
		String keyword = request.getParameter("keyword");
		
		
		// 첨부파일 가져오기(서블릿 기능 이용)
		Part part = request.getPart("attach"); // getParameter로 안됨:  request.getPart("") 객체 사용
		String fileName = getFileName(part);
		
		System.out.println(fileName); // pexels-jimbear-2454790.jpg
		
		// 서버로 전송된 파일 저장(서버 특정 폴더)
		String saveDir = "c:\\upload";
		if(!fileName.isEmpty()) { // 비어있지 !않으면
			// 파일명 중복 처리 => 고유키값_파일명
			UUID uuid = UUID.randomUUID();
			// File.separator : / or \ (운영체재에 맞춰서 역슬래쉬 혹은 슬래쉬 넣어줌)
			// c:\\upload\\70e21c5e-f63c-4669-8238-725b9e8999f8_pexels-gochrisgoxyz-1643409.jpg
			File f = new File(saveDir + File.separator + uuid + "_" + fileName);
			part.write(f.toString());
		// file DB 에 저장(
			insertDto.setAttach(f.getName()); // 70e21c5e-f63c-4669-8238-725b9e8999f8_pexels-gochrisgoxyz-1643409.jpg
		}
		
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		boolean insertFlag = service.create(insertDto);
		
		
		//4. return
		if (insertFlag) {
			// ==1 은  전체조회 페이지로 (등록된거 다시 보여주기)
			path += "?page="+page+"&amount="+amount+"&criteria="+criteria+"&keyword="+keyword;
			
		}else {
			path += "/board/create.jsp";
		}
		return new ActionForward(path, true);
	}
	
	// 첨부파일가져오기
	private String getFileName(Part part) {
	// content-disposition : attachment; filename =파일이름.확장자
		String  header = part.getHeader("content-disposition");
		String[] arr = header.split(";");
		for (int i = 0; i < arr.length; i++) {
			String temp = arr[i];
			if(temp.trim().startsWith("filename")) { // filename =파일이름.확장자 찾아와서
				return temp.substring(temp.indexOf("=")+2, temp.length()-1); // 파일이름.확장자 만 추출
			}
			
		}
		return "";
	}

}
