package action;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BookDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;

@AllArgsConstructor
public class BookDeleteAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		int code = Integer.parseInt(request.getParameter("code"));
		String keyword = request.getParameter("keyword");
		
		// 2. Service 호출
		BookService service = new BookServiceImpl();
		boolean deleteFlag = service.delete(code);
		
		// 4. page return
		// ==1 (성공) /list.do => BasicServlet 에 갈 곳 둠
		if(!deleteFlag) {
			//0 (실패) => 제자리
			path  = "/modify.do?code="+code;
			
		}else {
			// URLEncoder.encode(keyword, "utf-8") 안하면 한글깨짐(검색어로 넘어갈때)
			path += "?keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		return new ActionForward(path, true);
	}

}
