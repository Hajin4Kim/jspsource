package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BoardDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;


@AllArgsConstructor
public class BoardUpdateAction implements Action {
	
	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 1. 가져오기
		BoardDTO updateDto = new BoardDTO();
		
		updateDto.setTitle(request.getParameter("title"));
		updateDto.setContent(request.getParameter("content"));
		updateDto.setBno(Integer.parseInt(request.getParameter("bno")));
		updateDto.setPassword(request.getParameter("password"));
		
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		boolean updateFlag = service.update(updateDto);
		
		
		//4. return
		if (updateFlag) {
			// ==1 은  상세조회 페이지로 (수정된거 다시 보여주기)
			path += "?bno="+updateDto.getBno();
		}else {
			path = "/modify.do?bno="+updateDto.getBno();
		}
		return new ActionForward(path, true);
	}

}
