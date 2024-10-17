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
		// 1. 가져오기 => 가져올 것 없음
		// 2. Service 호출
		BookService service = new BookServiceImpl();
		List<BookDTO> list = service.list();
		
		request.setAttribute("list", list);
		// 3. (request.setAttribute)이면 당연히 => forward 이면 당연히 => false
		return new ActionForward(path, false);
	}

}
