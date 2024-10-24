package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BoardDTO;
import dto.SearchDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardReadAction implements Action {

	private String path;

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. 가져오기
		int bno = Integer.parseInt(request.getParameter("bno"));
		// 페이지 나누기
		int page = Integer.parseInt(request.getParameter("page"));
		int amount = Integer.parseInt(request.getParameter("amount"));

		// 검색기능 추가
		String criteria = request.getParameter("criteria");
		String keyword = request.getParameter("keyword");
		SearchDTO searchDTO = new SearchDTO(criteria, keyword, page, amount);
		
		
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		BoardDTO dto = service.getRow(bno);

		request.setAttribute("dto", dto);
		request.setAttribute("searchDTO", searchDTO); // 검색, 페이지나누기

		// 4. return
		return new ActionForward(path, false);
	}

}
