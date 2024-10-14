<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 사용자의 입력값 가져오기: request 객체
request.setCharacterEncoding("utf-8");
String name = request.getParameter("name");

// name 값을 a 태그로 이동하는 다른 페이지까지 사용할 수 있도록 scope 늘리기 : session 사용
// setAttribute("key", value); key - 같은 scope 안에서 중복 안됨, value - 객체 저장 가능

session.setAttribute("name", name);
%>
<h3><%=name%></h3>
<h4>
	<a href="request_scope2.jsp">이동하기</a>
</h4>