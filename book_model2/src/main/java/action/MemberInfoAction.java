package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.BookDTO;
import dto.ChangeDTO;
import dto.MemberDTO;
import lombok.AllArgsConstructor;
import service.BookService;
import service.BookServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;

@AllArgsConstructor
public class MemberInfoAction implements Action {

	private String path;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 가져오기 
		ChangeDTO changeDto = new ChangeDTO();	
		changeDto.setUserid(request.getParameter("userid"));
		changeDto.setCurrentPassword(request.getParameter("current_password"));
		changeDto.setChangePassword(request.getParameter("change_password"));
		
		
		// 2. Service 호출
		MemberService service = new MemberServiceImpl();
		boolean updateFlag = service.changePassword(changeDto);
		
		// 4. 페이지 이동
		if(updateFlag) { //성공할 경우
			HttpSession session = request.getSession();
			session.invalidate();
			
		}else {
			path = "/member/info.jsp"; //실패할 경우
		}
		
		return new ActionForward(path, true);
	}

}
