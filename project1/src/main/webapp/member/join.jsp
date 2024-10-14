<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	// 한글 처리
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("id");
	String password = request.getParameter("password");
	String name = request.getParameter("name");
	String tel = request.getParameter("tel");
	%>

	<ul>
		<li>id : <%=id%></li>
		<li>name : <%=name%></li>
		<li>password : <%=password%></li>
		<li>tel : <%=tel%></li>
	</ul>
</body>
</html>