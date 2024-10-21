package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import action.BoardListAction;
import action.BoardReadAction;
import action.BoardUpdateAction;



/**
 * Servlet implementation class BasicServlet
 */
@WebServlet("*.do")
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("utf-8");
		
		// Tomcat 서버의 path 수정을 하지 않았다면...
		// RequestURI => /프로젝트명/경로명	=> /model2/login.do
		// ContextPath => /프로젝트명 		=> /model2
		// "/model2/login.do".substring(ContextPath)경로명만 추출 => login.do
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String cmd = requestUri.substring(contextPath.length());
		
//		// syso
//		System.out.println("requestUri : "+requestUri);
//		System.out.println("contextPath : "+contextPath);
		System.out.println("cmd : "+cmd);
		
		Action action = null;
		
		if (cmd.equals("/list.do")) { // 전체목록
			action = new BoardListAction("/board/list.jsp");
		} else if(cmd.equals("/read.do")) { //상세조회 페이지
			action = new BoardReadAction("/board/read.jsp");
		} else if(cmd.equals("/modify.do")) { // 수정화면
			// modify는 Action 은똑같이
			action = new BoardReadAction("/board/modify.jsp");
		}else if(cmd.equals("/update.do")) { // 업데이트 실제 수행
			// 수정 성공 후 상세조회로 이동
			action = new BoardUpdateAction("/read.do");
		}
		
		ActionForward af = null;
		
		try {
			af = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(af.isRedirect()) {
			response.sendRedirect(af.getPath());
		}else {
			RequestDispatcher rd = request.getRequestDispatcher(af.getPath());
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
