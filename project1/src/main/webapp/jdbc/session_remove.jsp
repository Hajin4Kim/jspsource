<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 원하는 세션 제거법 (session.setAttrubute 명을 가져온다)
	// session.removeAttribute("loginDTO");

	// 전체 세션 제거법
	session.invalidate();
	
	// 페이지 이동
	response.sendRedirect("main.jsp");

%>
