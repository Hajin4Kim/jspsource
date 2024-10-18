package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BookDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;

@AllArgsConstructor
public class BookCreateAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		BookDTO insertDto = new BookDTO();
		insertDto.setCode(Integer.parseInt(request.getParameter("code")));
		insertDto.setTitle(request.getParameter("title"));
		insertDto.setWriter(request.getParameter("writer"));
		insertDto.setPrice(Integer.parseInt(request.getParameter("price")));
		insertDto.setDescription((request.getParameter("description")));

		// 2. Service 호출
		BookService service = new BookServiceImpl();
		boolean insertFlag = service.insert(insertDto);
		
		// 4. page return
		if(insertFlag) {
			// ==1 은  /read.do 페이지로 (insert한거 보여주기)
			// read.do?code=
			path +="?code="+insertDto.getCode();
		}else {
			// ==0 은 (수정실패) create 페이지로
			path = "/book/create.jsp";
		}
		
		return new ActionForward(path, true); // true = 아무것도 안담음
		
	}

}
