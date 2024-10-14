<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 사용자의 입력값 가져오기: request 객체
	
	// request.setCharacterEncoding("utf-8");
	// String name = request.getParameter("name");
	
	// session 에 저장한 값 불러오기 (기본적으로 object 형태이므로 강제형변환 casting 해준다)
	String name = (String)(session.getAttribute("name"));
%>
<h3><%=name %></h3>
<h4><a href="">이동하기</a></h4>