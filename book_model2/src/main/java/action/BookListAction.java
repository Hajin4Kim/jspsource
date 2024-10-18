package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BookDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;

@AllArgsConstructor
public class BookListAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 => 검색어 오는 경우 /(일반 리스트는 가져올것 없음)
		String keyword = request.getParameter("keyword");
		
		// 2. Service 호출
		BookService service = new BookServiceImpl();
		List<BookDTO> list = service.list(keyword);
		
		request.setAttribute("list", list);
		request.setAttribute("keyword", keyword); //${} jstl 사용하여 부르는 것 가능해짐
		
		// 3. (request.setAttribute)이면 당연히 => forward 이면 당연히 => false
		return new ActionForward(path, false);
	}

}
