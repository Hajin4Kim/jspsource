<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 쿠키(Cookie는 배열) 가져오기
Cookie[] cookies = request.getCookies();

// 향상된 for문
for (Cookie c : cookies) {
	out.print("<p>" + c.getName() + " : " + c.getValue() + "</p>");
}
%>