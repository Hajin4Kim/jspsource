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
public class MemberLoginAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		MemberDTO loginDto = new MemberDTO();
		loginDto.setUserid(request.getParameter("userid"));
		loginDto.setPassword(request.getParameter("password"));
		
		
		// 2. Service 호출
		MemberService service = new MemberServiceImpl();
		MemberDTO dto = service.login(loginDto);
		
		// 4. 페이지 이동
		if(dto != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginDto", dto);
			path += "?keyword=";
		}else {
			path = "/member/login.jsp";
		}
		
		return new ActionForward(path, true);
	}

}
