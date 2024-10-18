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
public class MemberLogoutAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 => 없음
		
		// 2. Service 호출 => 없음
		
		// 세션제거
		HttpSession session = request.getSession();
		session.removeAttribute("loginDto");
		
		// 4. 페이지 이동
		
		return new ActionForward(path, true);
	}

}
