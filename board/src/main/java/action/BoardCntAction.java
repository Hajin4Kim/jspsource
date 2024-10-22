package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BoardDTO;
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
	
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		
		// 조회수 업데이트 로직 추가
		service.hitUpdate(bno);
		
		path += "?bno="+bno;
		
		//4. return
		return new ActionForward(path, true);
	}

}
