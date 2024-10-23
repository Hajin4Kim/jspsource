package action;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dto.BoardDTO;
import lombok.AllArgsConstructor;
import service.BoardService;
import service.BoardServiceImpl;


@AllArgsConstructor
public class BoardReplyAction implements Action {
	
	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 1. 가져오기
		BoardDTO replyDto = new BoardDTO();
		
		replyDto.setName(request.getParameter("name"));
		replyDto.setPassword(request.getParameter("password"));
		replyDto.setTitle(request.getParameter("title"));
		replyDto.setContent(request.getParameter("content"));
		
		//(jsp 에서 처리한) hidden 부모정보 가져오기
		replyDto.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		replyDto.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		replyDto.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		replyDto.setBno(Integer.parseInt(request.getParameter("bno")));
		
		// 2. Service 호출
		BoardService service = new BoardServiceImpl();
		boolean replyFlag = service.reply(replyDto);
		
		
		//4. return
		if (replyFlag) {
		}else {
			path = "/replyView.do?bno="+replyDto.getBno();
		}
		return new ActionForward(path, true);
	}
	


}
