package action;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BoardDTO;
import dto.SearchDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;

@AllArgsConstructor
public class BoardCntAction implements Action {

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
		String keyword = URLEncoder.encode(request.getParameter("keyword"), "utf-8"); // SQL Argument exception


		// 2. Service 호출
		BoardService service = new BoardServiceImpl();

		// 조회수 업데이트 로직 추가
		service.hitUpdate(bno);

		path += "?bno=" + bno + "&page="+page+"&amount="+amount+"&criteria="+criteria+"&keyword="+keyword;

		// 4. return
		return new ActionForward(path, true);
	}

}
