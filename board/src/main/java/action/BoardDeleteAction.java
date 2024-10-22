package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.BoardDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;


@AllArgsConstructor
public class BoardDeleteAction implements Action {
	
	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 1. 가져오기
		BoardDTO deleteDto = new BoardDTO();
		
		deleteDto.setBno(Integer.parseInt(request.getParameter("bno")));
		deleteDto.setPassword(request.getParameter("password"));
		
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		boolean deleteFlag = service.delete(deleteDto);
		
		
		//4. return
		if (!deleteFlag) {
			// ==0 실패 : 제자리
			path += "/modify.do?bno= "+deleteDto.getBno();
		}//else { //==1 은  리스트 페이지로 (삭제된거 다시 보여주기)}

		
		return new ActionForward(path, true);
	}

}
