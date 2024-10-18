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
public class BookUpdateAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		BookDTO updateDto = new BookDTO();
		updateDto.setCode(Integer.parseInt(request.getParameter("code")));
		updateDto.setPrice(Integer.parseInt(request.getParameter("price")));
		updateDto.setDescription(request.getParameter("description"));

		//검색 시 추가
		String keyword = request.getParameter("keyword");
		
		// 2. Service 호출
		BookService service = new BookServiceImpl();
		boolean updateFlag = service.update(updateDto);
		
		// 4. page return
		if(updateFlag) {
			// ==1 은  상세조회 페이지로 (수정된거 다시 보여주기)
			// read.do?code=
			path +="?code="+updateDto.getCode()+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}else {
			// ==0 은 (수정실패) modify 페이지로
			path = "/modify.do?code="+updateDto.getCode()+"&keyword="+URLEncoder.encode(keyword, "utf-8");
		}
		
		return new ActionForward(path, true); // true = 아무것도 안담음
		
	}

}
