package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BookDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;

@AllArgsConstructor
public class BookReadAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		int code = Integer.parseInt(request.getParameter("code"));
		String keyword = request.getParameter("keyword"); // 키워드 검색으로 들어온 화면=> 목록 누르면 다시 키워드 검색 페이지로 이동
		
		// 2. Service 호출
		BookService service = new BookServiceImpl();
		BookDTO dto = service.read(code);
		
		request.setAttribute("dto", dto);
		request.setAttribute("keyword", keyword);
		
		// 3. (request.setAttribute)이면 당연히 => forward 이면 당연히 => false
		return new ActionForward(path, false);
	}

}
