<%@page import="dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	// leave.jsp 사용자 입력값(userid, password) 가져오기
	String userid = request.getParameter("userid");
	String password = request.getParameter("password");
	
	// MemberDAO delete 호출
	MemberDAO dao = new MemberDAO();
	int deleteRow = dao.delete(userid, password);

	// 결과에 따라 이동
	if (deleteRow == 1) {
	// == 1 : session 제거, main.jsp 로 이동
	session.invalidate();
	response.sendRedirect("main.jsp");
	} else {
		// == 0 : leave.jsp 로 이동
		response.sendRedirect("leave.jsp");
	}
%>
