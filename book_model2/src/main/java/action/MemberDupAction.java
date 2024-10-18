package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.BookDTO;
import dto.MemberDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;

@AllArgsConstructor
public class MemberDupAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		String userid = request.getParameter("userid");
		
		// 2. Service 호출
		MemberService service = new MemberServiceImpl();
		boolean dupFlag = service.duplicateId(userid);
		
		// 4. 페이지 이동
		if(dupFlag) {
			request.setAttribute("dup", "true");
		}else {
			request.setAttribute("dup", "false");
		}
		
		return new ActionForward(path, false); // request.setAttribute => 항상 false
	}

}
